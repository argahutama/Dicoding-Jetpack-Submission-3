package com.arga.jetpack.submission3.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.arga.jetpack.submission3.BuildConfig.IMAGE_URL
import com.arga.jetpack.submission3.R
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.databinding.ActivityTvShowDetailBinding
import com.arga.jetpack.submission3.presentation.activity.MainActivity.Companion.EXTRA_ID
import com.arga.jetpack.submission3.presentation.viewmodel.TvShowViewModel
import com.arga.jetpack.submission3.util.Utilization.Companion.errorToast
import com.arga.jetpack.submission3.util.Utilization.Companion.glideOption
import com.arga.jetpack.submission3.util.Utilization.Companion.successToast
import com.arga.jetpack.submission3.util.ViewModelFactory
import com.arga.jetpack.submission3.vo.Status
import com.bumptech.glide.Glide

class TvShowDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        val tvShowId = intent.extras?.getInt(EXTRA_ID)
        tvShowId?.let { viewModel.setSelectedDetail(it) }

        if (tvShowId != null) viewModel.selectedTvShow.observe(this, { tvShow ->
            if (tvShow != null) {
                with(binding) {
                    when (tvShow.status) {
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            progressBar.visibility = View.GONE
                            tvShow.data?.isFavorite?.let { setFavoriteState(it, binding) }
                            tvShow.data?.let { loadTvShowDetail(it, binding) }
                            fabFavorite.setOnClickListener {
                                viewModel.setFavoriteTvShow()
                                if (tvShow.data?.isFavorite!!)
                                    successToast(
                                        this@TvShowDetailActivity,
                                        resources.getString(R.string.success_remove_to_favorite)
                                    )
                                else successToast(
                                    this@TvShowDetailActivity,
                                    resources.getString(R.string.success_add_to_favorite)
                                )
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            errorToast(
                                this@TvShowDetailActivity,
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

    private fun loadTvShowDetail(tvShowDetail: TvShowEntity, binding: ActivityTvShowDetailBinding) {
        binding.progressBar.visibility = View.GONE
        binding.llContent.visibility = View.VISIBLE

        Glide.with(this)
            .load(IMAGE_URL + tvShowDetail.backdropPath)
            .apply(glideOption)
            .into(binding.ivBigPoster)
        Glide.with(this)
            .load(IMAGE_URL + tvShowDetail.posterPath)
            .apply(glideOption)
            .into(binding.ivSmallPoster)
        binding.tvTitle.text = tvShowDetail.name
        binding.tvFirstOnAir.text =
            resources.getString(R.string.first_on_air, tvShowDetail.firstAirDate)
        binding.tvRating.text = tvShowDetail.voteAverage.toString()
        binding.tvOverview.text = tvShowDetail.overview
    }

    private fun setFavoriteState(state: Boolean, binding: ActivityTvShowDetailBinding) {
        with(binding) {
            if (state) fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this@TvShowDetailActivity,
                    R.drawable.ic_baseline_favorite_filled_white_24
                )
            )
            else fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this@TvShowDetailActivity,
                    R.drawable.ic_baseline_favorite_border_white_24
                )
            )
        }
    }
}