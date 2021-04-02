package com.dumdumbich.sketchbook.githubclient.data.repository

import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.dumdumbich.sketchbook.githubclient.data.db.room.entity.GitHubUserEntity
import com.dumdumbich.sketchbook.githubclient.data.network.github.api.IDataSource
import com.dumdumbich.sketchbook.githubclient.data.network.service.INetworkStatus
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.domain.interactor.IGitHubUsersInteractor
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GitHubUsers(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: Database
) : IGitHubUsersInteractor {

    override fun getUsers(): Single<List<GitHubUser>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                api.getUsers()
                    .flatMap { users ->
                        Single.fromCallable {
                            val roomUsers = users.map { user ->
                                GitHubUserEntity(user.id, user.login, user.avatarUrl, user.reposUrl)
                            }
                            db.userDao.insert(roomUsers)
                            users
                        }
                    }
            } else {
                Single.fromCallable {
                    db.userDao.getAll().map { roomUser ->
                        GitHubUser(
                            roomUser.id,
                            roomUser.login,
                            roomUser.avatarUrl,
                            roomUser.reposUrl
                        )
                    }
                }
            }
        }.subscribeOn(Schedulers.io())

}