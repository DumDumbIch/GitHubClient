package com.dumdumbich.sketchbook.githubclient.ui.pages.repository

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class RepositoryPresenter(val router: Router, val repository: GitHubRepository) :
    MvpPresenter<IRepositoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setId(repository.id)
        viewState.setTitle(repository.name)
        viewState.setForksCount(repository.forksCount)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}