package com.arga.jetpack.submission3.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arga.jetpack.submission3.databinding.FragmentMovieBinding
import com.arga.jetpack.submission3.presentation.adapter.MovieAdapter
import com.arga.jetpack.submission3.presentation.viewmodel.MovieViewModel
import com.arga.jetpack.submission3.util.ViewModelFactory

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        val moviesAdapter = MovieAdapter(context)

        viewModel.movies.observe(viewLifecycleOwner, { data ->
            binding.progressBar.visibility = View.GONE
            moviesAdapter.submitList(data.data)
        })

        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

    }
}