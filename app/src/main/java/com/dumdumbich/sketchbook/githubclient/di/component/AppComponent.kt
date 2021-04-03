package com.dumdumbich.sketchbook.githubclient.di.component

import com.dumdumbich.sketchbook.githubclient.di.module.*
import com.dumdumbich.sketchbook.githubclient.ui.main.MainActivity
import com.dumdumbich.sketchbook.githubclient.ui.main.MainPresenter
import com.dumdumbich.sketchbook.githubclient.ui.pages.repositories.RepositoriesPresenter
import com.dumdumbich.sketchbook.githubclient.ui.pages.repository.RepositoryPresenter
import com.dumdumbich.sketchbook.githubclient.ui.pages.user.UserPresenter
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.UsersPresenter
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.list.UsersRVAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        NavigationModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ResourceModule::class,
        SchedulerModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(usersRVAdapter: UsersRVAdapter)
    fun inject(userPresenter: UserPresenter)
    fun inject(repositoriesPresenter: RepositoriesPresenter)
    fun inject(repositoryPresenter: RepositoryPresenter)
}