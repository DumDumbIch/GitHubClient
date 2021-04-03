package com.dumdumbich.sketchbook.githubclient.ui.pages.user

import android.util.Log
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class UserPresenter(private val router: Router, private val user: GitHubUser) :
    MvpPresenter<IUserView>() {


    override fun onFirstViewAttach() {
        Log.d("GITHUB_CLIENT", "UserPresenter(): onFirstViewAttach()")
        super.onFirstViewAttach()
        viewState.setLogin(user.login)
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

}