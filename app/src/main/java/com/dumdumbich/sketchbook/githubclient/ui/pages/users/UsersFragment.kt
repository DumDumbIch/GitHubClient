package com.dumdumbich.sketchbook.githubclient.ui.pages.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dumdumbich.sketchbook.githubclient.data.repository.GitHubUsersRepo
import com.dumdumbich.sketchbook.githubclient.databinding.FragmentUsersBinding
import com.dumdumbich.sketchbook.githubclient.ui.App
import com.dumdumbich.sketchbook.githubclient.ui.navigator.AndroidScreens
import com.dumdumbich.sketchbook.githubclient.ui.navigator.IBackClickListener
import com.dumdumbich.sketchbook.githubclient.ui.pages.users.list.UsersRVAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(), IUsersView, IBackClickListener {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private val presenter by moxyPresenter {
        UsersPresenter(
            GitHubUsersRepo(),
            App.instance.router,
            AndroidScreens(),
            AndroidSchedulers.mainThread()
        )
    }

    private var ui: FragmentUsersBinding? = null
    private var adapter: UsersRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUsersBinding.inflate(inflater, container, false).also {
        ui = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        ui = null
    }

    override fun init() {
        ui?.rvUsers?.layoutManager = LinearLayoutManager(requireContext())
        adapter = UsersRVAdapter(presenter.usersListPresenter)
        ui?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun isBackPressed() = presenter.backClick()

}