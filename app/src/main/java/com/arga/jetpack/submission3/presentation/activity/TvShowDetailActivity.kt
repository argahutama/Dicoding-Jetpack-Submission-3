package com.arga.jetpack.submission3.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arga.jetpack.submission3.R
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.databinding.ActivityTvShowDetailBinding
import com.arga.jetpack.submission3.util.Utilization.Companion.glideOption
import com.arga.jetpack.submission3.util.ViewModelFactory
import com.arga.jetpack.submission3.presentation.viewmodel.TvShowViewModel
import com.bumptech.glide.Glide

class TvShowDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        val tvShowId = intent.extras?.getInt("tvShowId")
        if (tvShowId != null) viewModel.getTvShowDetail(tvShowId).observe(
            this, { loadTvShowDetail(it, binding) }
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun loadTvShowDetail(tvShowDetail: TvShowEntity, binding: ActivityTvShowDetailBinding) {
        binding.progressBar.visibility = View.GONE
        binding.llContent.visibility = View.VISIBLE

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${tvShowDetail.backdropPath}")
            .apply(glideOption)
            .into(binding.ivBigPoster)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${tvShowDetail.posterPath}")
            .apply(glideOption)
            .into(binding.ivSmallPoster)
        binding.tvTitle.text = tvShowDetail.name
        binding.tvFirstOnAir.text =
            resources.getString(R.string.first_on_air, tvShowDetail.firstAirDate)
        binding.tvRating.text = tvShowDetail.voteAverage.toString()
        binding.tvOverview.text = tvShowDetail.overview
    }
}