package com.dumdumbich.sketchbook.githubclient.ui.pages.repositories

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IRepositoriesView  : MvpView {
    fun init()
    fun updateList()
}