package com.example.kinopoiskfintech.presentation.popularmovies

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.kinopoiskfintech.databinding.FragmentPopularFilmsBinding
import com.example.kinopoiskfintech.presentation.BaseFragment
import com.example.kinopoiskfintech.KinopoiskApp
import com.example.kinopoiskfintech.presentation.mainfragment.listeners.ListItemClickListener
import com.example.kinopoiskfintech.presentation.ViewModelFactory
import com.example.kinopoiskfintech.presentation.adapter.MovieListAdapter
import com.example.kinopoiskfintech.presentation.mainfragment.listeners.LoadingStateListener
import com.example.kinopoiskfintech.utils.ResourceState
import com.example.kinopoiskfintech.utils.getQueryChangeFlow
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PopularMoviesFragment :
    BaseFragment<FragmentPopularFilmsBinding>(FragmentPopularFilmsBinding::inflate) {

    @Inject
    lateinit var filmsAdapter: MovieListAdapter

    private val component by lazy {
        (requireActivity().application as KinopoiskApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PopularMoviesViewModel::class.java]
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
        viewModel.isError.onEach { isError ->
            if(isError) showNoConnection() else hideNoConnection()
        }.launchIn(lifecycleScope)
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
                if(movies.isNotEmpty()){
                    hideNotFound()
                    filmsAdapter.submitList(movies)
                }else showNotFound()
            }.launchIn(lifecycleScope)
    }

    private fun setupRecyclerView() {
        binding.rvPopular.adapter = filmsAdapter
        filmsAdapter.onFilmItemClickListener = { movieId ->
            listItemClickListener.onMovieClick(movieId = movieId)
        }
        filmsAdapter.onFilmItemLongClickListener = { movie ->
            viewModel.changeMovieFavouriteStatus(movie)
        }
        filmsAdapter.onReachEndListener = {
            viewModel.getMovies()
        }
    }

    private fun hideSearch() {
        with(binding.mainFragmentToolbar) {
            searchView.visibility = ViewPager2.GONE
            backImage.visibility = ViewPager2.GONE
            toolbarTitle.visibility = ViewPager2.VISIBLE
            starImage.visibility = ViewPager2.VISIBLE
        }
    }

    private fun retryLoadData(){
        hideNoConnection()
        viewModel.reloadMovies()
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
                hideSearch()
                hideNotFound()
            }
            searchView
                .getQueryChangeFlow()
                .debounce(500)
                .onEach { query ->
                    viewModel.getMoviesByQuery(query)
                }.launchIn(lifecycleScope)

        }
    }

    private fun showNotFound(){
        with(binding){
            rvPopular.visibility = GONE
            notFound.visibility = VISIBLE
        }
    }
    private fun hideNotFound(){
        with(binding){
            rvPopular.visibility = VISIBLE
            notFound.visibility = GONE
        }
    }

    private fun showNoConnection(){
        binding.noConnectionError.retryButton.setOnClickListener {
            retryLoadData()
        }
        binding.noConnectionError.noConnectionError.visibility = VISIBLE
        binding.rvPopular.visibility = GONE
        loadingStateListener.onLoadingEnd()
    }

    private fun hideNoConnection(){
        binding.noConnectionError.noConnectionError.visibility = GONE
        binding.rvPopular.visibility = VISIBLE
    }

    companion object {
        const val FRAGMENT_ID = 0
        const val FRAGMENT_TITLE = "Популярные"
    }

}