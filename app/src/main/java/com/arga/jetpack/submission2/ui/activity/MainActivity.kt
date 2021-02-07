package com.arga.jetpack.submission2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arga.jetpack.submission2.databinding.ActivityMainBinding
import com.arga.jetpack.submission2.ui.adapter.SectionPagerAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val mFragmentManager = supportFragmentManager
        val sectionsPagerAdapter =
            SectionPagerAdapter(this, mFragmentManager)
        activityMainBinding.viewPager.adapter = sectionsPagerAdapter
        activityMainBinding.tabs.setupWithViewPager(activityMainBinding.viewPager)
        supportActionBar?.elevation = 0f
    }
}