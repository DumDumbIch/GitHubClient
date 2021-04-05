package com.dumdumbich.sketchbook.githubclient.ui.pages.users

import android.util.Log
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.domain.interactor.IGitHubUsersInteractor
import com.dumdumbich.sketchbook.githubclient.ui.navigator.IScreens
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.list.IUserItemView
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.list.IUsersListPresenter
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject
import javax.inject.Named

class UsersPresenter() : MvpPresenter<IUsersView>() {

    @field:Named("ui")
    @Inject
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var interactor: IGitHubUsersInteractor

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens


    class UsersListPresenter : IUsersListPresenter {

        val users = mutableListOf<GitHubUser>()
        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            user.login.let { view.setLogin(it) }
            user.avatarUrl?.let { view.loadAvatar(it) }
        }

        override fun getCount() = users.size

    }

    private val compositeDisposable = CompositeDisposable()
    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        Log.d("GITHUB_CLIENT", "UsersPresenter(): onFirstViewAttach()")
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            Log.d(
                "GITHUB_CLIENT",
                "UsersPresenter(): onFirstViewAttach() - usersListPresenter.itemClickListener"
            )
            val user = usersListPresenter.users[itemView.pos]
            router.navigateTo(screens.repositories(user))
        }
    }

    private fun loadData() {
        Log.d("GITHUB_CLIENT", "UsersPresenter(): loadData()")
        usersListPresenter.users.clear()
        val disposable = interactor.getUsers()
            .observeOn(uiScheduler)
            .subscribe(
                { user ->
                    Log.d(
                        "GITHUB_CLIENT",
                        "UsersPresenter(): loadData() - subscribe on users success"
                    )
                    usersListPresenter.users.addAll(user)
                    viewState.updateList()
                },
                { error ->
                    Log.d(
                        "GITHUB_CLIENT",
                        "UsersPresenter(): loadData() - subscribe on users error"
                    )
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
        Log.d("GITHUB_CLIENT", "UsersPresenter(): onDestroy()")
        compositeDisposable.dispose()
        super.onDestroy()
    }

}