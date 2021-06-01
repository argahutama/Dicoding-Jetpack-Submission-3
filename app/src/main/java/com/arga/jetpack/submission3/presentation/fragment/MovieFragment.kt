package com.arga.jetpack.submission3.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arga.jetpack.submission3.R
import com.arga.jetpack.submission3.databinding.FragmentMovieBinding
import com.arga.jetpack.submission3.presentation.adapter.MovieAdapter
import com.arga.jetpack.submission3.presentation.viewmodel.MovieViewModel
import com.arga.jetpack.submission3.util.ViewModelFactory
import com.arga.jetpack.submission3.vo.Status
import www.sanju.motiontoast.MotionToast

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        val moviesAdapter = MovieAdapter(context)

        viewModel.movies.observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when (movies.status) {
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        moviesAdapter.submitList(movies.data)
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        MotionToast.darkColorToast(
                            requireActivity(),
                            resources.getString(R.string.error),
                            resources.getString(R.string.there_is_an_error),
                            MotionToast.TOAST_ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireActivity(), R.font.montserrat)
                        )
                    }
                }
            }
        })

        with(binding.rvMovies){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapter
        }
    }
}