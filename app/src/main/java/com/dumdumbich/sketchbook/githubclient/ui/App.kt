package com.dumdumbich.sketchbook.githubclient.ui

import android.app.Application
import android.util.Log
import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.dumdumbich.sketchbook.githubclient.di.component.AppComponent
import com.dumdumbich.sketchbook.githubclient.di.component.DaggerAppComponent
import com.dumdumbich.sketchbook.githubclient.di.module.AppModule

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        Log.d("GITHUB_CLIENT", "App(): onCreate()")
        super.onCreate()
        instance = this
        Database.create(this)
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onTerminate() {
        Log.d("GITHUB_CLIENT", "App(): onTerminate()")
        super.onTerminate()
    }

}