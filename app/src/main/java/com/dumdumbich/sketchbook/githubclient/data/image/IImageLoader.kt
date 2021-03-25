package com.dumdumbich.sketchbook.githubclient.data.image

interface IImageLoader<T> {
    fun load(url: String, container: T)
}