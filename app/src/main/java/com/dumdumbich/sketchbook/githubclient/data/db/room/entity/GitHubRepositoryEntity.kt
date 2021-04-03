package com.dumdumbich.sketchbook.githubclient.data.db.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = GitHubUserEntity::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class GitHubRepositoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val forksCount: Int,
    var userId: String
)