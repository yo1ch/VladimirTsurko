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

class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding>(FragmentMovieDetailsBinding::inflate) {

    private val component by lazy {
        (requireActivity().application as KinopoiskApp).component
    }

    private val args by navArgs<MovieDetailsFragmentArgs>()
    private val movie by lazy {
        args.movie
    }

    override fun onStart() {
        super.onStart()
        setTransluentStatusbar()
        setStatusBarIconTheme(isDarkTheme = true)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.imageViewArrow) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                top = insets.top
            )
            v.updateLayoutParams<MarginLayoutParams> {
                this.topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
        setupScreen()

    }

    private fun setupScreen() {
        with(binding) {
            Glide.with(root)
                .load(movie.posterUrl)
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
                        setInfo()
                        return false
                    }
                })
                .into(imagePoster)
            imageViewArrow.setOnClickListener { navigateBack() }
        }
    }

    private fun setInfo() {
        with(binding) {
            tvMovieTitle.text = movie.nameRu
            tvMovieDescription.text = movie.description
            tvGenres.text = movie.genres
            tvCountries.text = movie.countries
            tvGenresTitle.visibility = VISIBLE
            tvCountriesTitle.visibility = VISIBLE
        }
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }
}