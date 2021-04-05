package com.dumdumbich.sketchbook.githubclient.di.module

import com.dumdumbich.sketchbook.githubclient.data.db.cache.IGitHubRepositoriesCache
import com.dumdumbich.sketchbook.githubclient.data.db.cache.IGitHubUsersCache
import com.dumdumbich.sketchbook.githubclient.data.network.api.github.retrofit.IGitHubAPI
import com.dumdumbich.sketchbook.githubclient.data.network.service.INetworkStatus
import com.dumdumbich.sketchbook.githubclient.data.repository.GitHubRepositories
import com.dumdumbich.sketchbook.githubclient.data.repository.GitHubUsers
import com.dumdumbich.sketchbook.githubclient.domain.interactor.IGitHubRepositoriesInteractor
import com.dumdumbich.sketchbook.githubclient.domain.interactor.IGitHubUsersInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun getUsers(
        api: IGitHubAPI,
        networkStatus: INetworkStatus,
        cache: IGitHubUsersCache
    ): IGitHubUsersInteractor = GitHubUsers(api, networkStatus, cache)

    @Singleton
    @Provides
    fun getRepositories(
        api: IGitHubAPI,
        networkStatus: INetworkStatus,
        cache: IGitHubRepositoriesCache
    ): IGitHubRepositoriesInteractor = GitHubRepositories(api, networkStatus, cache)

}