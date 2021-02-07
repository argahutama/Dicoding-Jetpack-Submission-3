package com.arga.jetpack.submission2.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arga.jetpack.submission2.R
import com.arga.jetpack.submission2.data.source.local.entity.TvShowDetail
import com.arga.jetpack.submission2.databinding.ActivityTvShowDetailBinding
import com.arga.jetpack.submission2.util.ViewModelFactory
import com.arga.jetpack.submission2.viewmodel.TvShowViewModel
import com.bumptech.glide.Glide

class TvShowDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityTvShowDetailBinding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(activityTvShowDetailBinding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        val tvShowId = intent.extras?.getInt("tvShowId")
        if (tvShowId != null) {
            viewModel.getTvShowDetail(tvShowId).observe(this, { loadTvShowDetail(it, activityTvShowDetailBinding) })
        }
    }

    private fun loadTvShowDetail(tvShowDetail: TvShowDetail, activityTvShowDetailBinding: ActivityTvShowDetailBinding) {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${tvShowDetail.backdropPath}")
            .into(activityTvShowDetailBinding.ivBigPoster)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${tvShowDetail.posterPath}")
            .into(activityTvShowDetailBinding.ivSmallPoster)
        activityTvShowDetailBinding.tvTitle.text = tvShowDetail.name
        activityTvShowDetailBinding.tvRelease.text = resources.getString(R.string.release_date, tvShowDetail.firstAirDate)
        activityTvShowDetailBinding.tvRating.text = tvShowDetail.voteAverage.toString()
        activityTvShowDetailBinding.tvOverview.text = tvShowDetail.overview
    }
}