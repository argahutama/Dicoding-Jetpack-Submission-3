package com.arga.jetpack.submission2.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arga.jetpack.submission2.R
import com.arga.jetpack.submission2.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission2.databinding.ActivityTvShowDetailBinding
import com.arga.jetpack.submission2.util.Utilization.Companion.glideOption
import com.arga.jetpack.submission2.util.ViewModelFactory
import com.arga.jetpack.submission2.viewmodel.TvShowViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class TvShowDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        val tvShowId = intent.extras?.getInt("tvShowId")
        if (tvShowId != null) {
            viewModel.getTvShowDetail(tvShowId).observe(
                this, { loadTvShowDetail(it, binding) }
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun loadTvShowDetail(tvShowDetail: TvShowEntity, activityTvShowDetailBinding: ActivityTvShowDetailBinding) {
        activityTvShowDetailBinding.progressBar.visibility = View.GONE
        activityTvShowDetailBinding.llContent.visibility = View.VISIBLE

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${tvShowDetail.backdropPath}")
            .apply(glideOption)
            .into(activityTvShowDetailBinding.ivBigPoster)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${tvShowDetail.posterPath}")
            .apply(glideOption)
            .into(activityTvShowDetailBinding.ivSmallPoster)
        activityTvShowDetailBinding.tvTitle.text = tvShowDetail.name
        activityTvShowDetailBinding.tvFirstOnAir.text = resources.getString(R.string.first_on_air, tvShowDetail.firstAirDate)
        activityTvShowDetailBinding.tvRating.text = tvShowDetail.voteAverage.toString()
        activityTvShowDetailBinding.tvOverview.text = tvShowDetail.overview
    }
}