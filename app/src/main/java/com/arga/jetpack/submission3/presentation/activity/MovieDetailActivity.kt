package com.arga.jetpack.submission3.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.arga.jetpack.submission3.BuildConfig.IMAGE_URL
import com.arga.jetpack.submission3.R
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.databinding.ActivityMovieDetailBinding
import com.arga.jetpack.submission3.presentation.activity.MainActivity.Companion.EXTRA_ID
import com.arga.jetpack.submission3.presentation.viewmodel.MovieViewModel
import com.arga.jetpack.submission3.util.Utilization
import com.arga.jetpack.submission3.util.Utilization.Companion.glideOption
import com.arga.jetpack.submission3.util.ViewModelFactory
import com.arga.jetpack.submission3.vo.Status
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        val movieId = intent.extras?.getInt(EXTRA_ID)

        movieId?.let { viewModel.setSelectedDetail(it) }

        if (movieId != null) viewModel.selectedMovie.observe(this, { movie ->
            if (movie != null) {
                with(binding) {
                    when (movie.status) {
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            progressBar.visibility = View.GONE
                            movie.data?.isFavorite?.let { setFavoriteState(it, binding) }
                            movie.data?.let { loadMovieDetail(it, binding) }
                            fabFavorite.setOnClickListener {
                                viewModel.setFavoriteMovie()
                                if (movie.data?.isFavorite!!) Utilization.successToast(
                                    this@MovieDetailActivity,
                                    resources.getString(R.string.success_remove_to_favorite)
                                )
                                else Utilization.successToast(
                                    this@MovieDetailActivity,
                                    resources.getString(R.string.success_add_to_favorite)
                                )
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Utilization.errorToast(
                                this@MovieDetailActivity,
                                resources.getString(R.string.there_is_an_error)
                            )
                        }
                    }
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun loadMovieDetail(movieDetail: MovieEntity, binding: ActivityMovieDetailBinding) {
        binding.progressBar.visibility = View.GONE
        binding.llContent.visibility = View.VISIBLE

        Glide.with(this)
            .load(IMAGE_URL + movieDetail.backdropPath)
            .apply(glideOption)
            .into(binding.ivBigPoster)
        Glide.with(this)
            .load(IMAGE_URL + movieDetail.posterPath)
            .apply(glideOption)
            .into(binding.ivSmallPoster)
        binding.tvTitle.text = movieDetail.title
        binding.tvRelease.text =
            resources.getString(R.string.release_date, movieDetail.releasedDate)
        binding.tvRating.text = movieDetail.voteAverage.toString()
        binding.tvOverview.text = movieDetail.overview
    }

    private fun setFavoriteState(state: Boolean, binding: ActivityMovieDetailBinding) {
        with(binding) {
            if (state) fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this@MovieDetailActivity,
                    R.drawable.ic_baseline_favorite_filled_white_24
                )
            )
            else fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this@MovieDetailActivity,
                    R.drawable.ic_baseline_favorite_border_white_24
                )
            )
        }
    }
}