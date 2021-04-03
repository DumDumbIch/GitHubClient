package com.dumdumbich.sketchbook.githubclient.data.db.room.cache

import android.util.Log
import com.dumdumbich.sketchbook.githubclient.data.db.cache.IGitHubRepositoriesCache
import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.dumdumbich.sketchbook.githubclient.data.db.room.entity.GitHubRepositoryEntity
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GitHubRepositoriesCache(val db: Database) : IGitHubRepositoriesCache {

    override fun getUserRepositories(user: GitHubUser): Single<List<GitHubRepository>> =
        Single.fromCallable {
            Log.d("GITHUB_CLIENT", "GitHubRepositoriesCache(): getUserRepositories()")
            val roomUser =
                db.userDao.findByLogin(user.login)
                    ?: throw RuntimeException("No such user in cache")
            return@fromCallable db.repositoryDao.findForUser(roomUser.id)
                .map { GitHubRepository(it.id, it.name, it.forksCount) }
        }.subscribeOn(Schedulers.io())

    override fun putUserRepositories(
        user: GitHubUser,
        repositories: List<GitHubRepository>
    ): Completable =
        Completable.fromAction {
            Log.d("GITHUB_CLIENT", "GitHubRepositoriesCache(): putUserRepositories()")
            val roomUser = db.userDao.findByLogin(user.login)
                ?: throw RuntimeException("No such user in cache")
            val roomRepos = repositories.map {
                GitHubRepositoryEntity(it.id, it.name ?: "", it.forksCount ?: 0, roomUser.id)
            }
            db.repositoryDao.insert(roomRepos)

        }.subscribeOn(Schedulers.io())

}