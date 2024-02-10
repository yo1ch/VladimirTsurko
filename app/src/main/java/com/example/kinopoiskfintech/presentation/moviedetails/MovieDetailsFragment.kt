package com.example.kinopoiskfintech.presentation.moviedetails

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.example.kinopoiskfintech.KinopoiskApp
import com.example.kinopoiskfintech.databinding.FragmentMovieDetailsBinding
import com.example.kinopoiskfintech.presentation.BaseFragment
import com.bumptech.glide.request.target.Target
import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.presentation.ViewModelFactory
import com.example.kinopoiskfintech.utils.ResourceState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding>(FragmentMovieDetailsBinding::inflate) {

    private val component by lazy {
        (requireActivity().application as KinopoiskApp).component
    }

    private val args by navArgs<MovieDetailsFragmentArgs>()
    private val movieId by lazy {
        args.movieId
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieDetailsViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        setTransluentStatusbar()
        setStatusBarIconTheme(isDarkTheme = true)
        viewModel.getMovieById(movieId)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.leftArrow) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                top = insets.top
            )
            v.updateLayoutParams<MarginLayoutParams> {
                this.topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
        binding.leftArrow.setOnClickListener { navigateBack() }
        observeData()
    }

    private fun observeData() {
        viewModel.movies
            .onEach { movie ->
                when (movie) {
                    is ResourceState.Loading -> {}
                    is ResourceState.Error -> {}
                    is ResourceState.Content -> {
                        showInfo(movie.content)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun showInfo(
        movie: Movie
    ) {
        with(movie) {
            with(binding) {
                Glide.with(root)
                    .load(posterUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            tvMovieTitle.text = nameRu
                            tvMovieDescription.text = description
                            tvGenres.text = genres
                            tvCountries.text = countries
                            tvGenresTitle.visibility = VISIBLE
                            tvCountriesTitle.visibility = VISIBLE
                            return false
                        }
                    })
                    .into(imagePoster)
            }
        }

    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }
}