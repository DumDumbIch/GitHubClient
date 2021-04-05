package com.dumdumbich.sketchbook.githubclient.di.module

import androidx.room.Room
import com.dumdumbich.sketchbook.githubclient.data.db.cache.IGitHubRepositoriesCache
import com.dumdumbich.sketchbook.githubclient.data.db.cache.IGitHubUsersCache
import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.dumdumbich.sketchbook.githubclient.data.db.room.cache.GitHubRepositoriesCache
import com.dumdumbich.sketchbook.githubclient.data.db.room.cache.GitHubUsersCache
import com.dumdumbich.sketchbook.githubclient.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun database(app: App) = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
        .build()

    @Singleton
    @Provides
    fun usersCache(db: Database): IGitHubUsersCache = GitHubUsersCache(db)

    @Singleton
    @Provides
    fun repositoriesCache(db: Database): IGitHubRepositoriesCache = GitHubRepositoriesCache(db)

}