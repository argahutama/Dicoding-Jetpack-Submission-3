package com.arga.jetpack.submission2.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arga.jetpack.submission2.R
import com.arga.jetpack.submission2.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission2.databinding.ActivityMovieDetailBinding
import com.arga.jetpack.submission2.util.ViewModelFactory
import com.arga.jetpack.submission2.viewmodel.MovieViewModel
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMovieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(activityMovieDetailBinding.root)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        val movieId = intent.extras?.getInt("movieId")
        if (movieId != null) {
            viewModel.getMovieDetail(movieId).observe(this, { loadMovieDetail(it, activityMovieDetailBinding) })
        }
    }

    private fun loadMovieDetail(movieDetail: MovieEntity, activityMovieDetailBinding: ActivityMovieDetailBinding) {
        activityMovieDetailBinding.progressBar.visibility = View.GONE
        activityMovieDetailBinding.llContent.visibility = View.VISIBLE

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieDetail.backdropPath}")
            .into(activityMovieDetailBinding.ivBigPoster)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieDetail.posterPath}")
            .into(activityMovieDetailBinding.ivSmallPoster)
        activityMovieDetailBinding.tvTitle.text = movieDetail.title
        activityMovieDetailBinding.tvRelease.text = resources.getString(R.string.release_date, movieDetail.releasedDate)
        activityMovieDetailBinding.tvRating.text = movieDetail.voteAverage.toString()
        activityMovieDetailBinding.tvOverview.text = movieDetail.overview
    }
}