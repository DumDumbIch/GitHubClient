package com.dumdumbich.sketchbook.githubclient.data.api

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IDataSource {

    @GET("users")
    fun getUsers() : Single<List<GitHubUser>>

    @GET("users/{user}/repos")
    fun getUserRepositories(@Path("user") user: String):Single<List<GitHubRepository>>

}