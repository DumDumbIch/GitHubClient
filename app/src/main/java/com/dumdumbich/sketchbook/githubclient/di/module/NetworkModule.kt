package com.dumdumbich.sketchbook.githubclient.di.module

import com.dumdumbich.sketchbook.githubclient.data.network.api.github.retrofit.IGitHubAPI
import com.dumdumbich.sketchbook.githubclient.data.network.service.INetworkStatus
import com.dumdumbich.sketchbook.githubclient.data.network.service.NetworkStatus
import com.dumdumbich.sketchbook.githubclient.ui.App
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Named("baseUrl")
    @Provides
    fun baseUrl(): String = "https://api.github.com"

    @Provides
    @Singleton
    fun api(@Named("baseUrl") baseUrl: String, gson: Gson): IGitHubAPI = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(IGitHubAPI::class.java)

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Provides
    @Singleton
    fun networkStatus(app: App): INetworkStatus = NetworkStatus(app)

}