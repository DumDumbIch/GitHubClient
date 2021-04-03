package com.dumdumbich.sketchbook.githubclient.di.module

import com.dumdumbich.sketchbook.githubclient.ui.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App = app

}