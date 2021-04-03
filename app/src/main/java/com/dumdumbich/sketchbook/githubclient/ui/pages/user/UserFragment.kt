package com.dumdumbich.sketchbook.githubclient.ui.pages.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dumdumbich.sketchbook.githubclient.databinding.FragmentUserBinding
import com.dumdumbich.sketchbook.githubclient.domain.entity.GitHubUser
import com.dumdumbich.sketchbook.githubclient.ui.App
import com.dumdumbich.sketchbook.githubclient.ui.navigator.IBackClickListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserFragment : MvpAppCompatFragment(), IUserView,
    IBackClickListener {

    companion object {
        private const val USER_ARG = "user"

        fun newInstance(user: GitHubUser) = UserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_ARG, user)
            }
        }
    }

    private val presenter by moxyPresenter {
        val user = arguments?.getParcelable<GitHubUser>(USER_ARG) as GitHubUser
        UserPresenter(user).apply {
            App.instance.appComponent.inject(this)
        }
    }

    private var ui: FragmentUserBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserBinding.inflate(inflater, container, false).also {
        ui = it
        Log.d("GITHUB_CLIENT", "UserFragment(): onCreateView()")
    }.root

    override fun onDestroyView() {
        Log.d("GITHUB_CLIENT", "UserFragment(): onDestroyView()")
        super.onDestroyView()
        ui = null
    }

    override fun setLogin(text: String) {
        ui?.tvUserLogin?.text = text
    }

    override fun isBackPressed() = presenter.backClick()

}