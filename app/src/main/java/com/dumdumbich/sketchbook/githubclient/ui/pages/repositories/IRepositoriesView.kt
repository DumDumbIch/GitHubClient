package com.dumdumbich.sketchbook.githubclient.ui.pages.repositories

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IRepositoriesView  : MvpView {
    fun init()
    fun updateList()
}