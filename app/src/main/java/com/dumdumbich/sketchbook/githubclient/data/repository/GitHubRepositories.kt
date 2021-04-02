package com.dumdumbich.sketchbook.githubclient.data.repository

import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.dumdumbich.sketchbook.githubclient.data.db.room.entity.GitHubRepositoryEntity
import com.dumdumbich.sketchbook.githubclient.data.network.github.api.IDataSource
import com.dumdumbich.sketchbook.githubclient.data.network.service.INetworkStatus
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.domain.interactor.IGitHubRepositoriesInteractor
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GitHubRepositories(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: Database
) : IGitHubRepositoriesInteractor {

    override fun getRepositories(user: GitHubUser): Single<List<GitHubRepository>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.reposUrl?.let { url ->
                    api.getRepositories(url)
                        .flatMap { repositories ->
                            Single.fromCallable {
                                val roomUser = db.userDao.findByLogin(user.login)
                                    ?: throw RuntimeException("No user in DB")
                                val roomRepos = repositories.map { repo ->
                                    GitHubRepositoryEntity(
                                        repo.id,
                                        repo.name,
                                        repo.forksCount,
                                        roomUser.id
                                    )
                                }
                                db.repositoryDao.insert(roomRepos)
                                repositories
                            }
                        }
                } ?: Single.error(RuntimeException("User has no repos url"))
            } else {
                Single.fromCallable {
                    val roomUser = db.userDao.findByLogin(user.login)
                        ?: throw RuntimeException("No user in DB")
                    db.repositoryDao.findForUser(roomUser.id).map { roomRepo ->
                        GitHubRepository(roomRepo.id, roomRepo.name, roomRepo.forksCount)
                    }
                }
            }
        }.subscribeOn(Schedulers.io())

}