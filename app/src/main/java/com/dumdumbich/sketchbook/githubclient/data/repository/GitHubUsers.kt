package com.dumdumbich.sketchbook.githubclient.data.repository

import android.util.Log
import com.dumdumbich.sketchbook.githubclient.data.db.cache.IGitHubUsersCache
import com.dumdumbich.sketchbook.githubclient.data.network.api.github.retrofit.IGitHubAPI
import com.dumdumbich.sketchbook.githubclient.data.network.service.INetworkStatus
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.domain.interactor.IGitHubUsersInteractor
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GitHubUsers(
    private val api: IGitHubAPI,
    private val networkStatus: INetworkStatus,
    private val cache: IGitHubUsersCache
) : IGitHubUsersInteractor {

    override fun getUsers(): Single<List<GitHubUser>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                Log.d("GITHUB_CLIENT", "GitHubUsers(): getUsers() - Internet online")
                api.getUsers()
                    .flatMap { users ->
                        cache.putUsers(users).toSingleDefault(users)
                    }
            } else {
                Log.d("GITHUB_CLIENT", "GitHubUsers(): getUsers() - Internet offline")
                cache.getUsers()
            }
        }.subscribeOn(Schedulers.io())

}