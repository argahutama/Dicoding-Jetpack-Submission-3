package com.arga.jetpack.submission2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arga.jetpack.submission2.R
import com.arga.jetpack.submission2.data.repository.local.entity.Item
import com.arga.jetpack.submission2.databinding.MoviesItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapter(private val context: Context?): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    private val list =  ArrayList<Item>()

    fun setData(movie: ArrayList<Item>){
        list.clear()
        list.addAll(movie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val itemsAcademyBinding = MoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsAcademyBinding)
    }


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class MovieViewHolder(private val binding: MoviesItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Item){
            with(binding){
                tvTitleMovies.text = movie.title
                tvDescriptionMovies.text = movie.overview
                Glide.with(itemView)
                    .load(movie.posterPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_refresh_black_24dp).error(R.drawable.ic_broken_image_black_24dp))
                    .centerCrop()
                    .into(imgMovies)
            }
        }
    }

}