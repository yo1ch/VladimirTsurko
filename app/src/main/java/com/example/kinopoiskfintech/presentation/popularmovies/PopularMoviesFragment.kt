package com.example.kinopoiskfintech.presentation.popularmovies

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.kinopoiskfintech.databinding.FragmentPopularFilmsBinding
import com.example.kinopoiskfintech.presentation.BaseFragment
import com.example.kinopoiskfintech.KinopoiskApp
import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.presentation.ListItemClickListener
import com.example.kinopoiskfintech.presentation.adapter.MovieListAdapter
import javax.inject.Inject

class PopularMoviesFragment :
    BaseFragment<FragmentPopularFilmsBinding>(FragmentPopularFilmsBinding::inflate) {

    @Inject
    lateinit var filmsAdapter: MovieListAdapter

    private val component by lazy {
        (requireActivity().application as KinopoiskApp).component
    }

    private val movie = Movie(
        filmId = 1,
        nameRu = "Name of film",
        year = 2000,
        countries = "США, Британия",
        genres = "Ужасы",
        posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/12345.jpg",
        posterUrlPreview = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/12345.jpg",
        description = "Какое-то там описание",
        isFavourite = true,
    )

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

    private fun animateRvAppearance(){
        binding.rvPopular.alpha = 0f
        binding.rvPopular
            .animate()
            .alpha(1f)
            .setDuration(200)
            .setListener(null)
    }
    private fun setupRecyclerView() {
        binding.rvPopular.adapter = filmsAdapter
        binding.rvPopular.itemAnimator = null
        animateRvAppearance()
        filmsAdapter.onFilmItemClickListener = { movieId ->
            listItemClickListener.onMovieClick(movieId = movieId)
        }
        filmsAdapter.onFilmItemLongClickListener = { }
        filmsAdapter.onReachEndListener = { }
        filmsAdapter.submitList(
            listOf(movie)
        )
    }

    companion object {
        const val FRAGMENT_ID = 0
        const val FRAGMENT_TITLE = "Популярные"
    }

}