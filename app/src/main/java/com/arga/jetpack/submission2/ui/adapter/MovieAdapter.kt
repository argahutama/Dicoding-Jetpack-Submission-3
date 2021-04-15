package com.arga.jetpack.submission2.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arga.jetpack.submission2.R
import com.arga.jetpack.submission2.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission2.databinding.MoviesItemBinding
import com.arga.jetpack.submission2.ui.activity.MovieDetailActivity
import com.arga.jetpack.submission2.util.Utilization.Companion.glideOption
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class MovieAdapter(context: Context?): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    private val activity = context as Activity
    private val list =  ArrayList<MovieEntity>()

    fun setData(movie: ArrayList<MovieEntity>){
        list.clear()
        list.addAll(movie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = MoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class MovieViewHolder(private val binding: MoviesItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieEntity){
            with(binding){
                tvTitleMovie.text = movie.title
                tvDescriptionMovie.text = movie.overview
                tvRating.text = movie.voteAverage.toString()
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .apply(glideOption)
                    .into(ivMovie)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                    intent.putExtra("movieId", movie.id)
                    activity.startActivity(intent)
                    activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
            }
        }
    }

}