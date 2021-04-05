package com.dumdumbich.sketchbook.githubclient.ui.pages.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.dumdumbich.sketchbook.githubclient.data.db.room.cache.ImagesCache
import com.dumdumbich.sketchbook.githubclient.data.network.service.NetworkStatus
import com.dumdumbich.sketchbook.githubclient.data.resource.image.glide.ImageLoader
import com.dumdumbich.sketchbook.githubclient.databinding.FragmentUsersBinding
import com.dumdumbich.sketchbook.githubclient.ui.App
import com.dumdumbich.sketchbook.githubclient.ui.navigator.IBackClickListener
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.list.UsersRVAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(), IUsersView, IBackClickListener {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private val presenter by moxyPresenter {
        UsersPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var ui: FragmentUsersBinding? = null
    private var adapter: UsersRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUsersBinding.inflate(inflater, container, false).also {
        ui = it
        Log.d("GITHUB_CLIENT", "UsersFragment(): onCreateView()")
    }.root

    override fun onDestroyView() {
        Log.d("GITHUB_CLIENT", "UsersFragment(): onDestroyView()")
        super.onDestroyView()
        ui = null
    }

    override fun init() {
        Log.d("GITHUB_CLIENT", "UsersFragment(): init()")
        ui?.rvUsers?.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        ui?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        Log.d("GITHUB_CLIENT", "UsersFragment(): updateList()")
        adapter?.notifyDataSetChanged()
    }

    override fun isBackPressed() = presenter.backClick()

}