package com.dumdumbich.sketchbook.githubclient.data.db.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GitHubUserEntity(
    @PrimaryKey val id: String,
    val login: String,
    val avatarUrl: String? = null,
    val reposUrl: String? = null
)