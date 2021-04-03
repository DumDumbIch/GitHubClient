package com.dumdumbich.sketchbook.githubclient.ui.pages.repositories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dumdumbich.sketchbook.githubclient.data.db.room.Database
import com.dumdumbich.sketchbook.githubclient.data.db.room.cache.GitHubRepositoriesCache
import com.dumdumbich.sketchbook.githubclient.data.network.github.api.ApiHolder
import com.dumdumbich.sketchbook.githubclient.data.network.service.NetworkStatus
import com.dumdumbich.sketchbook.githubclient.data.repository.GitHubRepositories
import com.dumdumbich.sketchbook.githubclient.databinding.FragmentRepositoriesBinding
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.ui.App
import com.dumdumbich.sketchbook.githubclient.ui.navigator.AndroidScreens
import com.dumdumbich.sketchbook.githubclient.ui.navigator.IBackClickListener
import com.dumdumbich.sketchbook.githubclient.ui.pages.repositories.list.RepositoriesRVAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class RepositoriesFragment : MvpAppCompatFragment(), IRepositoriesView, IBackClickListener {

    companion object {
        private const val USER_ARG = "user"

        fun newInstance(user: GitHubUser) = RepositoriesFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_ARG, user)
            }
        }
    }

    private val presenter by moxyPresenter {
        val user = arguments?.getParcelable<GitHubUser>(USER_ARG) as GitHubUser
        RepositoriesPresenter(
            GitHubRepositories(
                ApiHolder.api,
                NetworkStatus(App.instance),
                GitHubRepositoriesCache(Database.getInstance())
            ),
            App.instance.router,
            AndroidScreens(),
            AndroidSchedulers.mainThread(),
            user
        )
    }

    private var ui: FragmentRepositoriesBinding? = null
    private var adapter: RepositoriesRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRepositoriesBinding.inflate(inflater, container, false).also {
        ui = it
        Log.d("GITHUB_CLIENT", "RepositoriesFragment(): onCreateView()")
    }.root

    override fun onDestroyView() {
        Log.d("GITHUB_CLIENT", "RepositoriesFragment(): onDestroyView()")
        super.onDestroyView()
        ui = null
    }

    override fun init() {
        Log.d("GITHUB_CLIENT", "RepositoriesFragment(): init()")
        ui?.rvRepositories?.layoutManager = LinearLayoutManager(context)
        adapter = RepositoriesRVAdapter(presenter.repositoriesListPresenter)
        ui?.rvRepositories?.adapter = adapter
    }

    override fun updateList() {
        Log.d("GITHUB_CLIENT", "RepositoriesFragment(): updateList()")
        adapter?.notifyDataSetChanged()
    }

    override fun isBackPressed() = presenter.backClick()

}