package com.example.kinopoiskfintech.presentation.favoritemovies

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.kinopoiskfintech.KinopoiskApp
import com.example.kinopoiskfintech.databinding.FragmentFavoriteFilmsBinding
import com.example.kinopoiskfintech.presentation.BaseFragment
import com.example.kinopoiskfintech.presentation.ListItemClickListener
import com.example.kinopoiskfintech.presentation.adapter.MovieListAdapter
import javax.inject.Inject

class FavoriteMoviesFragment : BaseFragment<FragmentFavoriteFilmsBinding>(FragmentFavoriteFilmsBinding::inflate) {

    @Inject
    lateinit var filmsAdapter: MovieListAdapter


    private val component by lazy{
        (requireActivity().application as KinopoiskApp).component
    }

    private lateinit var listItemClickListener: ListItemClickListener
    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        listItemClickListener = parentFragment as ListItemClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvFavorite.adapter = filmsAdapter
        binding.rvFavorite.itemAnimator = null
        filmsAdapter.onFilmItemClickListener = { listItemClickListener.click() }
        filmsAdapter.onFilmItemLongClickListener = { }
        filmsAdapter.onReachEndListener = { }
    }
    companion object{
        const val FRAGMENT_ID = 1
        const val FRAGMENT_TITLE = "Избранное"
    }
}