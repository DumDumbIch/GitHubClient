package com.dumdumbich.sketchbook.githubclient.ui.pages.repository

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dumdumbich.sketchbook.githubclient.databinding.FragmentRepositoryBinding
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubRepository
import com.dumdumbich.sketchbook.githubclient.ui.App
import com.dumdumbich.sketchbook.githubclient.ui.navigator.IBackClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class RepositoryFragment : MvpAppCompatFragment(), IRepositoryView, IBackClickListener {

    companion object {
        private const val REPOSITORY_ARG = "repository"

        fun newInstance(repository: GitHubRepository) = RepositoryFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPOSITORY_ARG, repository)
            }
        }
    }

    private var ui: FragmentRepositoryBinding? = null

    private val presenter: RepositoryPresenter by moxyPresenter {
        val repository =
            arguments?.getParcelable<GitHubRepository>(REPOSITORY_ARG) as GitHubRepository
        RepositoryPresenter(App.instance.router, repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentRepositoryBinding.inflate(inflater, container, false).also {
            ui = it
            Log.d("GITHUB_CLIENT", "RepositoryFragment(): onCreateView()")
        }.root

    override fun init() {}

    override fun setId(text: String) {
        ui?.tvId?.text = text
    }

    override fun setTitle(text: String) {
        ui?.tvTitle?.text = text
    }

    override fun setForksCount(text: String) {
        ui?.tvForksCount?.text = text
    }

    override fun isBackPressed() = presenter.backPressed()

    override fun onDestroyView() {
        Log.d("GITHUB_CLIENT", "RepositoryFragment(): onDestroyView()")
        super.onDestroyView()
        ui = null
    }

}