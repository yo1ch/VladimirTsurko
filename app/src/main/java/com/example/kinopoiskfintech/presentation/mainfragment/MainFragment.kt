package com.example.kinopoiskfintech.presentation.mainfragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.kinopoiskfintech.databinding.FragmentMainBinding
import com.example.kinopoiskfintech.domain.models.Movie
import com.example.kinopoiskfintech.presentation.BaseFragment
import com.example.kinopoiskfintech.presentation.ListItemClickListener
import com.example.kinopoiskfintech.presentation.favoritemovies.FavoriteMoviesFragment
import com.example.kinopoiskfintech.presentation.popularmovies.PopularMoviesFragment


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate),
    ListItemClickListener {

    private lateinit var pagerAdapter: MainFragmentPagerAdapter


    override fun onStart() {
        super.onStart()
        setupStatusBarColor()
        setStatusBarIconTheme(isDarkTheme = false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        pagerAdapter = MainFragmentPagerAdapter(childFragmentManager, lifecycle)
        with(binding.fragmentPager){
            adapter = pagerAdapter
            isUserInputEnabled = false
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        PopularMoviesFragment.FRAGMENT_ID -> {
                            with(binding) {
                                buttonsPanel.popularButton.isSelected = true
                                buttonsPanel.favoriteButton.isSelected = false
                                mainFragmentToolbar.toolbarTitle.text =
                                    PopularMoviesFragment.FRAGMENT_TITLE
                            }
                        }

                        FavoriteMoviesFragment.FRAGMENT_ID -> {
                            with(binding) {
                                buttonsPanel.favoriteButton.isSelected = true
                                buttonsPanel.popularButton.isSelected = false
                                mainFragmentToolbar.toolbarTitle.text =
                                    FavoriteMoviesFragment.FRAGMENT_TITLE
                            }
                        }
                    }
                }
            })
        }
        setupToggleButtons()
    }
    private fun setupToggleButtons() {
        with(binding.buttonsPanel){
            popularButton.setOnClickListener {
                binding.fragmentPager.currentItem = 0
            }
            favoriteButton.setOnClickListener {
                binding.fragmentPager.currentItem = 1
            }
        }
    }


    override fun click() {
        findNavController().navigate(MainFragmentDirections.actionMovieDetails(
            Movie(
                filmId = 1,
                nameRu = "Кавказская пленница",
                year = 2000,
                countries = "Россия, Грузия",
                genres = "Комедия, Ужасы",
                posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp/301.jpg",
                posterUrlPreview = "",
                description = "Вполне крутой фильмец",
                isFavourite = true,
            )
        ))
    }

}