package com.arga.jetpack.submission2.util

import com.arga.jetpack.submission2.R
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class Utilization {
    companion object {
        val glideOption: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.ic_broken_image_black_24dp)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform()
    }
}