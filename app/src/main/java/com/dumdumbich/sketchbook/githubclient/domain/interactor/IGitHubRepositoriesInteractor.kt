package com.dumdumbich.sketchbook.githubclient.domain.interactor

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Single

interface IGitHubRepositoriesInteractor {
    fun getRepositories(user: GitHubUser): Single<List<GitHubRepository>>
}