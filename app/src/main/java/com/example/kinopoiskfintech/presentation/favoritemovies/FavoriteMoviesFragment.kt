package com.example.kinopoiskfintech.presentation.favoritemovies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.kinopoiskfintech.KinopoiskApp
import com.example.kinopoiskfintech.databinding.FragmentFavoriteFilmsBinding
import com.example.kinopoiskfintech.presentation.BaseFragment
import com.example.kinopoiskfintech.presentation.ViewModelFactory
import com.example.kinopoiskfintech.presentation.mainfragment.listeners.ListItemClickListener
import com.example.kinopoiskfintech.presentation.adapter.MovieListAdapter
import com.example.kinopoiskfintech.presentation.mainfragment.listeners.LoadingStateListener
import com.example.kinopoiskfintech.utils.ResourceState
import com.example.kinopoiskfintech.utils.getQueryChangeFlow
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
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
        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movies
            .onEach { movies ->
                when (movies) {
                    is ResourceState.Loading -> {
                        loadingStateListener.onLoadingStart()
                    }

                    is ResourceState.Error -> {
                        loadingStateListener.onLoadingEnd()
                    }

                    is ResourceState.Content -> {
                        loadingStateListener.onLoadingEnd()
                        filmsAdapter.submitList(movies.content)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeMoviesByQuery() {
        viewModel.moviesByQuery
            .onEach { movies ->
                if (movies.isNotEmpty()) {
                    hideNotFound()
                    filmsAdapter.submitList(movies)
                }else showNotFound()
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

    private fun hideSearch() {
        with(binding.mainFragmentToolbar) {
            searchView.visibility = ViewPager2.GONE
            backImage.visibility = ViewPager2.GONE
            toolbarTitle.visibility = ViewPager2.VISIBLE
            starImage.visibility = ViewPager2.VISIBLE
        }
    }

    private fun showSearch() {
        with(binding.mainFragmentToolbar) {
            searchView.visibility = ViewPager2.VISIBLE
            backImage.visibility = ViewPager2.VISIBLE
            toolbarTitle.visibility = ViewPager2.GONE
            starImage.visibility = ViewPager2.GONE
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupToolbar() {
        with(binding.mainFragmentToolbar) {
            toolbarTitle.text = FRAGMENT_TITLE
            starImage.setOnClickListener {
                observeMoviesByQuery()
                showSearch()
            }
            backImage.setOnClickListener {
                when (val res = viewModel.movies.value) {
                    is ResourceState.Content -> filmsAdapter.submitList(res.content)
                    else -> {}
                }
                hideNotFound()
                hideSearch()
            }
            searchView
                .getQueryChangeFlow()
                .debounce(500)
                .onEach { query ->
                    viewModel.getFavouriteByQuery(query)
                }.launchIn(lifecycleScope)

        }
    }

    private fun showNotFound(){
        with(binding){
            rvFavorite.visibility = View.GONE
            notFound.visibility = View.VISIBLE
        }
    }

    private fun hideNotFound(){
        with(binding){
            rvFavorite.visibility = View.VISIBLE
            notFound.visibility = View.GONE
        }
    }

    companion object {
        const val FRAGMENT_ID = 1
        const val FRAGMENT_TITLE = "Избранное"
    }
}