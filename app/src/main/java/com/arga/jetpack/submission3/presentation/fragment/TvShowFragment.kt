package com.arga.jetpack.submission3.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arga.jetpack.submission3.R
import com.arga.jetpack.submission3.databinding.FragmentTvShowBinding
import com.arga.jetpack.submission3.presentation.adapter.TvShowAdapter
import com.arga.jetpack.submission3.presentation.viewmodel.TvShowViewModel
import com.arga.jetpack.submission3.util.Utilization.Companion.errorToast
import com.arga.jetpack.submission3.util.ViewModelFactory
import com.arga.jetpack.submission3.vo.Status

class TvShowFragment : Fragment() {

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
        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        val tvShowAdapter = TvShowAdapter(context)

        viewModel.getTvShows().observe(viewLifecycleOwner, { tvShows ->
            if (tvShows != null) {
                when (tvShows.status) {
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.llLayoutNoItem.visibility =
                            if (tvShows.data?.size == 0) View.VISIBLE else View.GONE
                        binding.progressBar.visibility = View.GONE
                        tvShowAdapter.submitList(tvShows.data)
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        errorToast(
                            requireActivity(),
                            resources.getString(R.string.there_is_an_error)
                        )
                    }
                }
            }
        })

        with(binding.rvTvshow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }

}