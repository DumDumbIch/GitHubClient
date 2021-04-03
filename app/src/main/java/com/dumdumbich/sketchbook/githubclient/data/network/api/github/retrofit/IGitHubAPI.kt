package com.dumdumbich.sketchbook.githubclient.data.network.api.github.retrofit

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface IGitHubAPI {

    @GET("users")
    fun getUsers() : Single<List<GitHubUser>>

    @GET
    fun getRepositories(@Url url: String): Single<List<GitHubRepository>>

}