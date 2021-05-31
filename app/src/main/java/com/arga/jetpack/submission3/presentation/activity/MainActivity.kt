package com.arga.jetpack.submission3.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.res.ResourcesCompat
import com.arga.jetpack.submission3.R
import com.arga.jetpack.submission3.databinding.ActivityMainBinding
import com.arga.jetpack.submission3.presentation.adapter.SectionPagerMainAdapter
import www.sanju.motiontoast.MotionToast

class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val sectionsPagerAdapter =
            SectionPagerMainAdapter(this, mFragmentManager)

        with(binding) {
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(binding.viewPager)
            ivFavorite.setOnClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true

        MotionToast.darkColorToast(
            this,
            resources.getString(R.string.warning),
            resources.getString(R.string.press_back_once_again_to_exit),
            MotionToast.TOAST_WARNING,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, R.font.montserrat)
        )

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}