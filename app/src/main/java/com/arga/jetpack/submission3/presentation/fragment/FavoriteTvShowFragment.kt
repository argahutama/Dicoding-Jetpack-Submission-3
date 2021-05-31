package com.arga.jetpack.submission3.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arga.jetpack.submission3.databinding.FragmentTvShowBinding
import com.arga.jetpack.submission3.presentation.adapter.TvShowAdapter
import com.arga.jetpack.submission3.presentation.viewmodel.TvShowViewModel
import com.arga.jetpack.submission3.util.ViewModelFactory

class FavoriteTvShowFragment : Fragment() {
    private lateinit var binding: FragmentTvShowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        val tvShowAdapter = TvShowAdapter(context)
        viewModel.tvShow.observe(viewLifecycleOwner, { data ->
            binding.progressBar.visibility = View.GONE
        })

        with(binding.rvTvshow) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }
}