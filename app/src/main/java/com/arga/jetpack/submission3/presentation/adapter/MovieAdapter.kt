package com.arga.jetpack.submission3.presentation.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arga.jetpack.submission3.BuildConfig.IMAGE_URL
import com.arga.jetpack.submission3.R
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.databinding.MoviesItemBinding
import com.arga.jetpack.submission3.presentation.activity.MainActivity.Companion.EXTRA_ID
import com.arga.jetpack.submission3.presentation.activity.MovieDetailActivity
import com.arga.jetpack.submission3.util.Utilization.Companion.glideOption
import com.bumptech.glide.Glide

class MovieAdapter(context: Context?) :
    PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private val activity = context as Activity

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = MoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) holder.bind(movie)
    }

    inner class MovieViewHolder(private val binding: MoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                tvTitleMovie.text = movie.title
                tvDescriptionMovie.text = movie.overview
                tvRating.text = movie.voteAverage.toString()
                Glide.with(itemView)
                    .load(IMAGE_URL + movie.posterPath)
                    .centerCrop()
                    .apply(glideOption)
                    .into(ivMovie)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                    intent.putExtra(EXTRA_ID, movie.id)
                    activity.startActivity(intent)
                    activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
            }
        }
    }
}