package com.dumdumbich.sketchbook.githubclient.data.repository

import com.dumdumbich.sketchbook.githubclient.data.api.IDataSource
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GitHubUsersRepo(private val api: IDataSource) : IGitHubUsersRepo {

    override fun getUsers(): Single<List<GitHubUser>> = api.getUsers().subscribeOn(Schedulers.io())

}