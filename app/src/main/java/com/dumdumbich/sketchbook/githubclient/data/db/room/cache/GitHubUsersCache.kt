package com.dumdumbich.sketchbook.githubclient.data.db.room.cache

import android.util.Log
import com.dumdumbich.sketchbook.githubclient.data.db.cache.IGitHubUsersCache
import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.dumdumbich.sketchbook.githubclient.data.db.room.entity.GitHubUserEntity
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GitHubUsersCache(private val db: Database) : IGitHubUsersCache {

    override fun getUsers(): Single<List<GitHubUser>> = Single.fromCallable {
        Log.d("GITHUB_CLIENT", "GitHubUsersCache(): getUsers()")
        db.userDao.getAll().map { cache ->
            GitHubUser(cache.id, cache.login, cache.avatarUrl, cache.reposUrl)
        }
    }

    override fun putUsers(users: List<GitHubUser>): Completable = Completable.fromAction {
        Log.d("GITHUB_CLIENT", "GitHubUsersCache(): putUsers()")
        val cache = users.map { user ->
            GitHubUserEntity(
                user.id,
                user.login,
                user.avatarUrl ?: "",
                user.reposUrl ?: ""
            )
        }
        db.userDao.insert(cache)
    }.subscribeOn(Schedulers.io())

}