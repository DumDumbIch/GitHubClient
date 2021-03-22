package com.dumdumbich.sketchbook.githubclient.ui.pages.users.list

interface IListPresenter<V> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}