package com.arga.jetpack.submission2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arga.jetpack.submission2.data.source.local.entity.Item
import com.arga.jetpack.submission2.databinding.FragmentTvShowBinding
import com.arga.jetpack.submission2.ui.adapter.TvShowAdapter
import com.arga.jetpack.submission2.util.ViewModelFactory
import com.arga.jetpack.submission2.viewmodel.TvShowViewModel

class TvShowFragment : Fragment() {

    private lateinit var fragmentTvShowBinding: FragmentTvShowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        fragmentTvShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        val tvShowAdapter = TvShowAdapter(context)
        viewModel.tvShow.observe(viewLifecycleOwner, { data ->
            fragmentTvShowBinding.progressBar.visibility = View.GONE
            tvShowAdapter.setData(data as ArrayList<Item>)
        })

        with(fragmentTvShowBinding.rvTvshow){
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }

}