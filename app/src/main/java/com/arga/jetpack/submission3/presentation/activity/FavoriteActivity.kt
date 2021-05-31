package com.arga.jetpack.submission3.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arga.jetpack.submission3.databinding.ActivityFavoriteBinding
import com.arga.jetpack.submission3.presentation.adapter.SectionPagerMainAdapter

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val sectionsPagerAdapter =
            SectionPagerMainAdapter(this, mFragmentManager)

        with(binding) {
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(binding.viewPager)
        }
    }
}