package com.arga.jetpack.submission3.util

import android.app.Activity
import androidx.core.content.res.ResourcesCompat
import com.arga.jetpack.submission3.R
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import www.sanju.motiontoast.MotionToast

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

        fun successToast(activity: Activity, message: String) {
            MotionToast.darkColorToast(
                activity,
                activity.resources.getString(R.string.success),
                message,
                MotionToast.TOAST_SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(activity, R.font.montserrat)
            )
        }

        fun errorToast(activity: Activity, message: String) {
            MotionToast.darkColorToast(
                activity,
                activity.resources.getString(R.string.error),
                message,
                MotionToast.TOAST_ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(activity, R.font.montserrat)
            )
        }

        fun warningToast(activity: Activity, message: String) {
            MotionToast.darkColorToast(
                activity,
                activity.resources.getString(R.string.warning),
                message,
                MotionToast.TOAST_WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(activity, R.font.montserrat)
            )
        }
    }
}