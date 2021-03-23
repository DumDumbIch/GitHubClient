package com.dumdumbich.sketchbook.githubclient.ui.pages.users

import com.dumdumbich.sketchbook.githubclient.data.repository.IGitHubUsersRepo
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.ui.navigator.IScreens
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.list.IUserItemView
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.list.IUsersListPresenter
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

class UsersPresenter(
    private val usersRepo: IGitHubUsersRepo,
    private val router: Router,
    private val screens: IScreens,
    private val uiScheduler: Scheduler
) : MvpPresenter<IUsersView>() {

    class UsersListPresenter : IUsersListPresenter {

        val users = mutableListOf<GitHubUser>()

        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
            view.loadAvatar(user.avatarUrl)
        }

        override fun getCount() = users.size

    }

    private val compositeDisposable = CompositeDisposable()
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
        val disposable = usersRepo.getUsers()
            .observeOn(uiScheduler)
            .subscribe(
                { user ->
                    usersListPresenter.users.addAll(user)
                    viewState.updateList()
                },
                { error ->
                    error.printStackTrace()
                }
            )
        compositeDisposable.add(disposable)
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

}