package com.example.kinopoiskfintech.presentation.favoritemovies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kinopoiskfintech.KinopoiskApp
import com.example.kinopoiskfintech.databinding.FragmentFavoriteFilmsBinding
import com.example.kinopoiskfintech.presentation.BaseFragment
import com.example.kinopoiskfintech.presentation.ViewModelFactory
import com.example.kinopoiskfintech.presentation.mainfragment.listeners.ListItemClickListener
import com.example.kinopoiskfintech.presentation.adapter.MovieListAdapter
import com.example.kinopoiskfintech.presentation.mainfragment.listeners.LoadingStateListener
import com.example.kinopoiskfintech.utils.ResourceState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FavoriteMoviesFragment :
    BaseFragment<FragmentFavoriteFilmsBinding>(FragmentFavoriteFilmsBinding::inflate) {

    @Inject
    lateinit var filmsAdapter: MovieListAdapter


    private val component by lazy {
        (requireActivity().application as KinopoiskApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavoriteMoviesViewModel::class.java]
    }

    private lateinit var listItemClickListener: ListItemClickListener
    private lateinit var loadingStateListener: LoadingStateListener
    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        listItemClickListener = parentFragment as ListItemClickListener
        loadingStateListener = parentFragment as LoadingStateListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movies
            .onEach { movies ->
                when(movies){
                    is ResourceState.Loading -> { loadingStateListener.onLoadingStart() }
                    is ResourceState.Error -> { loadingStateListener.onLoadingEnd() }
                    is ResourceState.Content -> {
                        loadingStateListener.onLoadingEnd()
                        filmsAdapter.submitList(movies.content)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun setupRecyclerView() {
        binding.rvFavorite.adapter = filmsAdapter
        filmsAdapter.onFilmItemClickListener = { movieId ->
            listItemClickListener.onMovieClick(movieId)
        }
        filmsAdapter.onFilmItemLongClickListener = { movie ->
            viewModel.changeMovieFavouriteStatus(movie)
        }
        filmsAdapter.onReachEndListener = { }
    }

    companion object {
        const val FRAGMENT_ID = 1
        const val FRAGMENT_TITLE = "Избранное"
    }
}