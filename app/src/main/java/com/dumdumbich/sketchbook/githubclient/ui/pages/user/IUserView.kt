package com.dumdumbich.sketchbook.githubclient.ui.pages.user

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IUserView : MvpView {
    fun setLogin(text: String)
}