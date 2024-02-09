package com.example.kinopoiskfintech.presentation.favoritemovies

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.kinopoiskfintech.KinopoiskApp
import com.example.kinopoiskfintech.databinding.FragmentFavoriteFilmsBinding
import com.example.kinopoiskfintech.presentation.BaseFragment
import com.example.kinopoiskfintech.presentation.adapter.MovieListAdapter
import javax.inject.Inject

class FavoriteMoviesFragment : BaseFragment<FragmentFavoriteFilmsBinding>(FragmentFavoriteFilmsBinding::inflate) {

    @Inject
    lateinit var filmsAdapter: MovieListAdapter


    private val component by lazy{
        (requireActivity().application as KinopoiskApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvFavorite.adapter = filmsAdapter
        binding.rvFavorite.itemAnimator = null
        filmsAdapter.onFilmItemClickListener = { }
        filmsAdapter.onFilmItemLongClickListener = { }
        filmsAdapter.onReachEndListener = { }
    }

}