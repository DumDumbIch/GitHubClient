package com.dumdumbich.sketchbook.githubclient.data.api

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IDataSource {

    @GET("users")
    fun getUsers() : Single<List<GitHubUser>>

}