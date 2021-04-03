package com.dumdumbich.sketchbook.githubclient.data.repository

import android.util.Log
import com.dumdumbich.sketchbook.githubclient.data.db.cache.IGitHubRepositoriesCache
import com.dumdumbich.sketchbook.githubclient.data.network.api.github.retrofit.IGitHubAPI
import com.dumdumbich.sketchbook.githubclient.data.network.service.INetworkStatus
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.domain.interactor.IGitHubRepositoriesInteractor
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GitHubRepositories(
    private val api: IGitHubAPI,
    private val networkStatus: INetworkStatus,
    private val cache: IGitHubRepositoriesCache
) : IGitHubRepositoriesInteractor {

    override fun getRepositories(user: GitHubUser): Single<List<GitHubRepository>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                Log.d("GITHUB_CLIENT", "GitHubRepositories(): getRepositories() - Internet online")
                user.reposUrl?.let { url ->
                    api.getRepositories(url)
                        .flatMap { repositories ->
                            cache.putUserRepositories(user, repositories)
                                .toSingleDefault(repositories)
                        }
                } ?: Single.error<List<GitHubRepository>>(RuntimeException("User has no repos url"))
                    .subscribeOn(Schedulers.io())   // redundant ?!
            } else {
                Log.d("GITHUB_CLIENT", "GitHubRepositories(): getRepositories() - Internet offline")
                cache.getUserRepositories(user)
            }.subscribeOn(Schedulers.io())
        }

}