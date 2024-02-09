package com.example.kinopoiskfintech.presentation.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.kinopoiskfintech.KinopoiskApp
import com.example.kinopoiskfintech.databinding.FragmentMovieDetailsBinding
import com.example.kinopoiskfintech.presentation.BaseFragment

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>(FragmentMovieDetailsBinding::inflate) {


    private val component by lazy {
        (requireActivity().application as KinopoiskApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}