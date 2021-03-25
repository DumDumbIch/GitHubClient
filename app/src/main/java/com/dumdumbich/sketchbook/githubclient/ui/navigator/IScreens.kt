package com.dumdumbich.sketchbook.githubclient.ui.navigator

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.github.terrakok.cicerone.Screen

interface IScreens {
    fun users(): Screen
    fun user(user: GitHubUser): Screen
    fun repositories(user: GitHubUser): Screen
}