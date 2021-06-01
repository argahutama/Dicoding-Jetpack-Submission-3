package com.arga.jetpack.submission3.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arga.jetpack.submission3.databinding.FragmentTvShowBinding
import com.arga.jetpack.submission3.presentation.adapter.FavoriteTvShowAdapter
import com.arga.jetpack.submission3.presentation.viewmodel.FavoriteTvShowViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[FavoriteTvShowViewModel::class.java]

        val favoriteTvShowAdapter = FavoriteTvShowAdapter(context)

        viewModel.getFavoriteTvShows().observe(viewLifecycleOwner, { tvShows ->
            binding.llLayoutNoItem.visibility = if (tvShows.size == 0) View.VISIBLE else View.GONE
            binding.progressBar.visibility = View.GONE
            favoriteTvShowAdapter.submitList(tvShows)
        })

        with(binding.rvTvshow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteTvShowAdapter
        }
    }
}