package com.dumdumbich.sketchbook.githubclient.data.db.room.cache

import android.util.Log
import com.dumdumbich.sketchbook.githubclient.data.db.cache.IImageCache
import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.dumdumbich.sketchbook.githubclient.data.db.room.entity.CachedImageEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest

class ImagesCache(private val db: Database, private val dir: File) : IImageCache {
    private fun String.md5() = hash("MD5")
    private fun String.hash(algorithm: String) =
        MessageDigest.getInstance(algorithm).digest(toByteArray())
            .fold("", { _, it -> "%02x".format(it) })

    override fun getBytes(url: String): Maybe<ByteArray?> = Maybe.fromCallable {
        Log.d("GITHUB_CLIENT", "ImagesCache(): getBytes()")
        db.imageDao.findByUrl(url)?.let {
            File(it.localPath).inputStream().readBytes()
        }
    }.subscribeOn(Schedulers.io())

    override fun saveImage(url: String, bytes: ByteArray): Completable =
        Completable.create { emitter ->
            Log.d("GITHUB_CLIENT", "ImagesCache(): saveImage()")
            if (!dir.exists() && !dir.mkdir()) {
                emitter.onError(IOException("Failed to create cache dir"))
                return@create
            }

            val fileFormat = if (url.contains(".jpg")) ".jpg" else ".png"
            val imageFile = File(dir, url.md5() + fileFormat)
            try {
                FileOutputStream(imageFile).use {
                    it.write(bytes)
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
            db.imageDao.insert(CachedImageEntity(url, imageFile.path))
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())

    override fun contains(url: String): Single<Boolean> {
        Log.d("GITHUB_CLIENT", "ImagesCache(): contains()")
        return Single.fromCallable { db.imageDao.findByUrl(url) != null }
            .subscribeOn(Schedulers.io())
    }

    override fun clear(): Completable = Completable.fromAction {
        Log.d("GITHUB_CLIENT", "ImagesCache(): clear()")
        db.imageDao.clear()
        dir.deleteRecursively()
    }.subscribeOn(Schedulers.io())

}