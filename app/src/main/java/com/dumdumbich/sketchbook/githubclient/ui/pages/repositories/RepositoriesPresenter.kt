package com.dumdumbich.sketchbook.githubclient.ui.pages.repositories

import com.dumdumbich.sketchbook.githubclient.data.repository.IGitHubUsersRepo
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.ui.navigator.IScreens
import com.dumdumbich.sketchbook.githubclient.ui.pages.repositories.list.IRepositoriesListPresenter
import com.dumdumbich.sketchbook.githubclient.ui.pages.repositories.list.IRepositoryItemView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

class RepositoriesPresenter(
    private val usersRepo: IGitHubUsersRepo,
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
            view.setName(repository.name)
        }

        override fun getCount() = repositories.size
    }

    private val compositeDisposable = CompositeDisposable()
    val repositoriesListPresenter = RepositoriesListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        repositoriesListPresenter.itemClickListener = { view ->
//            val repository = repositoriesListPresenter.repositories[view.pos]
            router.navigateTo(screens.users())
        }
    }

    private fun loadData() {
        repositoriesListPresenter.repositories.clear()
        val disposable = usersRepo.getUserRepositories(user)
            .observeOn(uiScheduler)
            .subscribe(
                { repository ->
                    repositoriesListPresenter.repositories.addAll(repository)
                    viewState.updateList()
                },
                { error ->
                    error.printStackTrace()
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

}