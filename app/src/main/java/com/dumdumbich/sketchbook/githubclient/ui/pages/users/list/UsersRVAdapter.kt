package com.dumdumbich.sketchbook.githubclient.ui.pages.users.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dumdumbich.sketchbook.githubclient.databinding.FragmentUsersItemBinding
import com.dumdumbich.sketchbook.githubclient.data.resource.image.IImageLoader

class UsersRVAdapter(
    private val presenter: IUsersListPresenter,
    val imageLoader: IImageLoader<ImageView>
) :
    RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            FragmentUsersItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount() = presenter.getCount()


    inner class ViewHolder(private val ui: FragmentUsersItemBinding) :
        RecyclerView.ViewHolder(ui.root),
        IUserItemView {

        override var pos = -1

        override fun setLogin(text: String) = with(ui) {
            tvLogin.text = text
        }

        override fun loadAvatar(url: String)  = with(ui) {
            imageLoader.load(url, ivAvatar)
        }
    }

}