package com.dumdumbich.sketchbook.githubclient.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitHubUser(
    @Expose val login: String,
    @Expose val avatarUrl: String,
    @Expose val reposUrl: String
) : Parcelable