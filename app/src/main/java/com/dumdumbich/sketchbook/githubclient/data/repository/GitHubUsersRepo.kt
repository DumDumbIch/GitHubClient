package com.dumdumbich.sketchbook.githubclient.data.repository

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Observable

class GitHubUsersRepo {

    private val users = listOf(
        GitHubUser("login_1"),
        GitHubUser("login_2"),
        GitHubUser("login_3"),
        GitHubUser("login_4"),
        GitHubUser("login_5"),
        GitHubUser("login_6"),
        GitHubUser("login_7")
    )

    fun getUsers(): Observable<List<GitHubUser>> = Observable.just(users)

}