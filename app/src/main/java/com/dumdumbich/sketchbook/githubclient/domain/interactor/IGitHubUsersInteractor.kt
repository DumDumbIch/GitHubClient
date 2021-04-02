package com.dumdumbich.sketchbook.githubclient.domain.interactor

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Single

interface IGitHubUsersInteractor {
    fun getUsers(): Single<List<GitHubUser>>
}