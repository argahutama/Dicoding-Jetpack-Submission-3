package com.arga.jetpack.submission2.presentation.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arga.jetpack.submission2.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission2.databinding.TvshowItemBinding

class TvShowAdapter(context: Context?): RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>(){

    private val activity = context as Activity
    private val list =  ArrayList<TvShowEntity>()

    fun setData(movie: ArrayList<TvShowEntity>){
        list.clear()
        list.addAll(movie)
        notifyDataSetChanged()
    }

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

    inner class TvShowViewHolder(private val binding: TvshowItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tvShow: TvShowEntity){
            with(binding){
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