package com.arga.jetpack.submission3.presentation.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arga.jetpack.submission3.R
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.databinding.TvshowItemBinding
import com.arga.jetpack.submission3.presentation.activity.TvShowDetailActivity
import com.arga.jetpack.submission3.util.Utilization.Companion.glideOption
import com.bumptech.glide.Glide

class FavoriteTvShowAdapter(context: Context?) :
    PagedListAdapter<TvShowEntity, FavoriteTvShowAdapter.TvShowViewHolder>(
        DIFF_CALLBACK
    ) {

    private val activity = context as Activity
    private val list = ArrayList<TvShowEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowViewHolder {
        val binding = TvshowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) =
        holder.bind(list[position])

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class TvShowViewHolder(private val binding: TvshowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                tvTitleTvShow.text = tvShow.name
                tvDescriptionTvShow.text = tvShow.overview
                tvRating.text = tvShow.voteAverage.toString()
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500${tvShow.posterPath}")
                    .apply(glideOption)
                    .into(ivTvShow)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, TvShowDetailActivity::class.java)
                    intent.putExtra("tvShowId", tvShow.id)
                    activity.startActivity(intent)
                    activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
            }
        }
    }

}