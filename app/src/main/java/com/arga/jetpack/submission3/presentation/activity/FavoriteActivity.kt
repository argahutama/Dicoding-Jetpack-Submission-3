package com.arga.jetpack.submission3.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arga.jetpack.submission3.R
import com.arga.jetpack.submission3.databinding.ActivityFavoriteBinding
import com.arga.jetpack.submission3.presentation.adapter.sectionpager.SectionPagerFavoriteAdapter

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val sectionsPagerAdapter =
            SectionPagerFavoriteAdapter(this, mFragmentManager)

        with(binding) {
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(binding.viewPager)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}