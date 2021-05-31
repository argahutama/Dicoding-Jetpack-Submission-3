package com.arga.jetpack.submission3.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arga.jetpack.submission3.R
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.databinding.ActivityMovieDetailBinding
import com.arga.jetpack.submission3.util.Utilization.Companion.glideOption
import com.arga.jetpack.submission3.util.ViewModelFactory
import com.arga.jetpack.submission3.presentation.viewmodel.MovieViewModel
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        val movieId = intent.extras?.getInt("movieId")
        if (movieId != null) viewModel.getMovieDetail(movieId).observe(
            this, { it.data?.let { it1 -> loadMovieDetail(it1, binding) } }
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun loadMovieDetail(movieDetail: MovieEntity, binding: ActivityMovieDetailBinding) {
        binding.progressBar.visibility = View.GONE
        binding.llContent.visibility = View.VISIBLE

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieDetail.backdropPath}")
            .apply(glideOption)
            .into(binding.ivBigPoster)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieDetail.posterPath}")
            .apply(glideOption)
            .into(binding.ivSmallPoster)
        binding.tvTitle.text = movieDetail.title
        binding.tvRelease.text =
            resources.getString(R.string.release_date, movieDetail.releasedDate)
        binding.tvRating.text = movieDetail.voteAverage.toString()
        binding.tvOverview.text = movieDetail.overview
    }
}