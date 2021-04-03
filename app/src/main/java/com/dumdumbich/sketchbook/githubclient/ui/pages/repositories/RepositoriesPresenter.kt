package com.dumdumbich.sketchbook.githubclient.ui.pages.repositories

import android.util.Log
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.domain.interactor.IGitHubRepositoriesInteractor
import com.dumdumbich.sketchbook.githubclient.ui.navigator.IScreens
import com.dumdumbich.sketchbook.githubclient.ui.pages.repositories.list.IRepositoriesListPresenter
import com.dumdumbich.sketchbook.githubclient.ui.pages.repositories.list.IRepositoryItemView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

class RepositoriesPresenter(
    private val interactor: IGitHubRepositoriesInteractor,
    private val router: Router,
    private val screens: IScreens,
    private val uiScheduler: Scheduler,
    private val user: GitHubUser
) : MvpPresenter<IRepositoriesView>() {

    class RepositoriesListPresenter : IRepositoriesListPresenter {

        val repositories = mutableListOf<GitHubRepository>()
        override var itemClickListener: ((IRepositoryItemView) -> Unit)? = null

        override fun bindView(view: IRepositoryItemView) {
            val repository = repositories[view.pos]
            repository.name?.let { view.setName(it) }
        }

        override fun getCount() = repositories.size
    }

    private val compositeDisposable = CompositeDisposable()
    val repositoriesListPresenter = RepositoriesListPresenter()

    override fun onFirstViewAttach() {
        Log.d("GITHUB_CLIENT", "RepositoriesPresenter(): onFirstViewAttach()")
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        repositoriesListPresenter.itemClickListener = { view ->
            Log.d("GITHUB_CLIENT", "RepositoriesPresenter(): onFirstViewAttach() - usersListPresenter.itemClickListener")
            val repository = repositoriesListPresenter.repositories[view.pos]
            router.navigateTo(screens.repository(repository))
        }
    }

    private fun loadData() {
        Log.d("GITHUB_CLIENT", "RepositoriesPresenter(): loadData()")
        val disposable = interactor.getRepositories(user)
            .observeOn(uiScheduler)
            .subscribe(
                { repository ->
                    Log.d("GITHUB_CLIENT", "RepositoriesPresenter(): loadData() - subscribe on users success")
                    repositoriesListPresenter.repositories.clear()
                    repositoriesListPresenter.repositories.addAll(repository)
                    viewState.updateList()
                },
                { error ->
                    Log.d("GITHUB_CLIENT", "RepositoriesPresenter(): loadData() - subscribe on users error")
                    error.printStackTrace()
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        Log.d("GITHUB_CLIENT", "RepositoriesPresenter(): onDestroy()")
        compositeDisposable.dispose()
        super.onDestroy()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

}