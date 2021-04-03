package com.dumdumbich.sketchbook.githubclient.data.db.cache

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IGitHubRepositoriesCache {
    fun getUserRepositories(user: GitHubUser): Single<List<GitHubRepository>>
    fun putUserRepositories(user: GitHubUser, repositories: List<GitHubRepository>): Completable
}