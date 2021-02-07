package com.arga.jetpack.submission2.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.arga.jetpack.submission2.R
import com.arga.jetpack.submission2.ui.fragment.MovieFragment
import com.arga.jetpack.submission2.ui.fragment.TvShowFragment

class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val TAB_TITLES = intArrayOf(
        R.string.tab_movie,
        R.string.tab_tv_show
    )
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment =
                MovieFragment()
            1 -> fragment =
                TvShowFragment()
        }
        return fragment as Fragment
    }
    override fun getPageTitle(position: Int): CharSequence {
        return mContext.resources.getString(TAB_TITLES[position])
    }
    override fun getCount(): Int {
        return 2
    }
}