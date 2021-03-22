package com.dumdumbich.sketchbook.githubclient.ui.pages.users

import com.dumdumbich.sketchbook.githubclient.data.repository.GitHubUsersRepo
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.ui.navigator.IScreens
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.list.IUserItemView
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.list.IUsersListPresenter
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class UsersPresenter(
    private val usersRepo: GitHubUsersRepo,
    private val router: Router,
    private val screens: IScreens
) : MvpPresenter<IUsersView>() {

    class UsersListPresenter : IUsersListPresenter {

        val users = mutableListOf<GitHubUser>()

        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }

        override fun getCount() = users.size

    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { view ->
            val user = usersListPresenter.users[view.pos]
            router.navigateTo(screens.user(user))
        }
    }

    private fun loadData() {
        usersListPresenter.users.clear()
        usersRepo.getUsers().subscribe(
            { user ->
                usersListPresenter.users.addAll(user)
            },
            { error ->
                error.printStackTrace()
            }
        )
        viewState.updateList()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}