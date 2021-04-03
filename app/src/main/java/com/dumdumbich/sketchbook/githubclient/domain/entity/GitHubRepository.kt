package com.dumdumbich.sketchbook.githubclient.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitHubRepository(
    @Expose val id: String,
    @Expose val name: String? = null,
    @Expose val forksCount: Int? = null
) : Parcelable