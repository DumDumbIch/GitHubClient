package com.dumdumbich.sketchbook.githubclient.data.resource.image.glide

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dumdumbich.sketchbook.githubclient.data.db.cache.IImageCache
import com.dumdumbich.sketchbook.githubclient.data.network.service.INetworkStatus
import com.dumdumbich.sketchbook.githubclient.data.resource.image.IImageLoader
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.io.ByteArrayOutputStream

class ImageLoader(val cache: IImageCache, private val networkStatus: INetworkStatus) :
    IImageLoader<ImageView> {

    override fun load(url: String, container: ImageView) {
        Log.d("GITHUB_CLIENT", "ImageLoader(): load()")
        networkStatus.isOnlineSingle()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isOnline ->
                if (isOnline) {
                    Log.d("GITHUB_CLIENT", "ImageLoader(): load() - Internet online")
                    Glide.with(container.context)
                        .asBitmap()
                        .load(url)
                        .listener(object : RequestListener<Bitmap> {

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Bitmap>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.d("GITHUB_CLIENT", "ImageLoader(): load() - listener: onLoadFailed()")
                                return true
                            }

                            override fun onResourceReady(
                                resource: Bitmap?,
                                model: Any?,
                                target: Target<Bitmap>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.d("GITHUB_CLIENT", "ImageLoader(): load() - listener: onResourceReady()")
                                val compressFormat =
                                    if (url.contains(".jpg")) Bitmap.CompressFormat.JPEG
                                    else Bitmap.CompressFormat.PNG
                                val stream = ByteArrayOutputStream()
                                resource?.compress(compressFormat, 100, stream)
                                val bytes = stream.use { it.toByteArray() }
                                cache.saveImage(url, bytes).blockingAwait()
                                return false
                            }

                        })
                        .into(container)
                } else {
                    Log.d("GITHUB_CLIENT", "ImageLoader(): load() - Internet offline")
                    cache.getBytes(url).observeOn(AndroidSchedulers.mainThread()).subscribe({
                        Glide.with(container.context)
                            .load(it)
                            .into(container)
                    }, {

                    })
                }
            }
    }

}