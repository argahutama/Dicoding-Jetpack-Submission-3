package com.arga.jetpack.submission2.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arga.jetpack.submission2.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)
    }
}