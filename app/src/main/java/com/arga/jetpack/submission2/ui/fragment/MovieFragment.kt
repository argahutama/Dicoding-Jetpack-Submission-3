package com.arga.jetpack.submission2.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arga.jetpack.submission2.data.source.local.entity.Item
import com.arga.jetpack.submission2.databinding.FragmentMovieBinding
import com.arga.jetpack.submission2.ui.adapter.MovieAdapter
import com.arga.jetpack.submission2.util.ViewModelFactory
import com.arga.jetpack.submission2.viewmodel.MovieViewModel

class MovieFragment : Fragment() {

    private lateinit var fragmentMovieBinding: FragmentMovieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        val moviesAdapter = MovieAdapter(context)

        viewModel.movie.observe(viewLifecycleOwner, { data ->
            fragmentMovieBinding.progressBar.visibility = View.GONE
            moviesAdapter.setData(data as ArrayList<Item>)
        })

        with(fragmentMovieBinding.rvMovies){
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

    }
}