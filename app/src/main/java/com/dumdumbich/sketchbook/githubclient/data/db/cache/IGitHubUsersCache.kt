package com.dumdumbich.sketchbook.githubclient.data.db.cache

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IGitHubUsersCache {
    fun getUsers(): Single<List<GitHubUser>>
    fun putUsers(users: List<GitHubUser>) : Completable
}