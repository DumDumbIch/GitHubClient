package com.dumdumbich.sketchbook.githubclient.ui.navigator

import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.ui.pages.repositories.RepositoriesFragment
import com.dumdumbich.sketchbook.githubclient.ui.pages.user.UserFragment
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.UsersFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(user: GitHubUser) = FragmentScreen { UserFragment.newInstance(user) }
    override fun repositories(user: GitHubUser) = FragmentScreen { RepositoriesFragment.newInstance(user) }
}