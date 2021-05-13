package com.arga.jetpack.submission2.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.res.ResourcesCompat
import com.arga.jetpack.submission2.R
import com.arga.jetpack.submission2.databinding.ActivityMainBinding
import com.arga.jetpack.submission2.presentation.adapter.SectionPagerAdapter
import www.sanju.motiontoast.MotionToast

class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager
        val sectionsPagerAdapter =
            SectionPagerAdapter(this, mFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true

        MotionToast.darkColorToast(this,
                resources.getString(R.string.warning),
                resources.getString(R.string.press_back_once_again_to_exit),
                MotionToast.TOAST_WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(this,R.font.montserrat))

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}