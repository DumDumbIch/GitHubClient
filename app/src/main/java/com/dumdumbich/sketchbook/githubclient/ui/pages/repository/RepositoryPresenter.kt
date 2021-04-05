package com.dumdumbich.sketchbook.githubclient.ui.pages.repository

import android.util.Log
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class RepositoryPresenter(val repository: GitHubRepository) :
    MvpPresenter<IRepositoryView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        Log.d("GITHUB_CLIENT", "RepositoryPresenter(): onFirstViewAttach()")
        super.onFirstViewAttach()
        viewState.init()
        viewState.setId(repository.id)
        viewState.setTitle(repository.name ?: "")
        viewState.setForksCount(repository.forksCount.toString())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        Log.d("GITHUB_CLIENT", "RepositoryPresenter(): onDestroy()")
        super.onDestroy()
    }

}