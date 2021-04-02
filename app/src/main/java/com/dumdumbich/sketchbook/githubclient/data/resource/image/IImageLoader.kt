package com.dumdumbich.sketchbook.githubclient.data.resource.image

interface IImageLoader<T> {
    fun load(url: String, container: T)
}