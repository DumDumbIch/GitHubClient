package com.dumdumbich.sketchbook.githubclient.data.db.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CachedImageEntity(
    @PrimaryKey val url: String,
    val localPath: String
)
