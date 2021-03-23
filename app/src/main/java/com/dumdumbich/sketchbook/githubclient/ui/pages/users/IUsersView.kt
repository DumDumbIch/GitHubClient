package com.dumdumbich.sketchbook.githubclient.ui.pages.users

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IUsersView : MvpView {
    fun init()
    fun updateList()
}