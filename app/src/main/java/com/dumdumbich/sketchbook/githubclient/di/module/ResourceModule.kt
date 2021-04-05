package com.dumdumbich.sketchbook.githubclient.di.module

import android.widget.ImageView
import com.dumdumbich.sketchbook.githubclient.data.db.cache.IImageCache
import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.dumdumbich.sketchbook.githubclient.data.db.room.cache.ImagesCache
import com.dumdumbich.sketchbook.githubclient.data.network.service.INetworkStatus
import com.dumdumbich.sketchbook.githubclient.data.resource.image.IImageLoader
import com.dumdumbich.sketchbook.githubclient.data.resource.image.glide.ImageLoader
import com.dumdumbich.sketchbook.githubclient.ui.App
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
class ResourceModule {

    @Named("cacheDir")
    @Singleton
    @Provides
    fun cacheDir(app: App): File = app.cacheDir

    @Singleton
    @Provides
    fun imageCache(database: Database, @Named("cacheDir") cacheDir: File): IImageCache =
        ImagesCache(database, cacheDir)

    @Singleton
    @Provides
    fun imageLoader(cache: IImageCache, networkStatus: INetworkStatus): IImageLoader<ImageView> =
        ImageLoader(cache, networkStatus)

}