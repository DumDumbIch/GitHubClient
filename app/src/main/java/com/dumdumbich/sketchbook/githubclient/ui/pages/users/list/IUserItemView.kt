package com.dumdumbich.sketchbook.githubclient.ui.pages.users.list

interface IUserItemView : IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}