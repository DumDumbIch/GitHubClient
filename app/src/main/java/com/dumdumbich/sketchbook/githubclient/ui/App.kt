package com.dumdumbich.sketchbook.githubclient.ui

import android.app.Application
import android.util.Log
import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    val router get() = cicerone.router

    override fun onCreate() {
        Log.d("GITHUB_CLIENT", "App(): onCreate()")
        super.onCreate()
        instance = this
        Database.create(this)
    }

    override fun onTerminate() {
        Log.d("GITHUB_CLIENT", "App(): onTerminate()")
        super.onTerminate()
    }

}