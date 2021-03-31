package com.dumdumbich.sketchbook.githubclient.ui.pages.repositories.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dumdumbich.sketchbook.githubclient.databinding.FragmentRepositoriesItemBinding

class RepositoriesRVAdapter(
    private val presenter: IRepositoriesListPresenter
) : RecyclerView.Adapter<RepositoriesRVAdapter.ViewHolder>()
{


    inner class ViewHolder(
        private val ui: FragmentRepositoriesItemBinding,

        ) :
        RecyclerView.ViewHolder(ui.root), IRepositoryItemView {

        override var pos: Int = -1

        override fun setName(text: String) = with(ui) {
            tvRepositoryName.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            FragmentRepositoriesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
            .apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount() = presenter.getCount()

}