package com.dumdumbich.sketchbook.githubclient.data.repository

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Single

interface IGitHubUsersRepo {

    fun getUsers(): Single<List<GitHubUser>>
    fun getUserRepositories(user: GitHubUser): Single<List<GitHubRepository>>

}