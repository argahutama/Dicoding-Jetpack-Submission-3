package com.arga.jetpack.submission3.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arga.jetpack.submission3.databinding.FragmentMovieBinding
import com.arga.jetpack.submission3.presentation.adapter.FavoriteMovieAdapter
import com.arga.jetpack.submission3.presentation.viewmodel.FavoriteMovieViewModel
import com.arga.jetpack.submission3.util.ViewModelFactory

class FavoriteMovieFragment : Fragment() {

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
        val viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

        val favoriteMovieAdapter = FavoriteMovieAdapter(context)

        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, { movies ->
            binding.llLayoutNoItem.visibility = if (movies.size == 0) View.VISIBLE else View.GONE
            binding.progressBar.visibility = View.GONE
            favoriteMovieAdapter.submitList(movies)
        })


        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteMovieAdapter
        }
    }
}