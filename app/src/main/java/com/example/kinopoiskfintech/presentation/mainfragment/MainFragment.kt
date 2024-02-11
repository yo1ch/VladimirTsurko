package com.example.kinopoiskfintech.presentation.mainfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.GONE
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.VISIBLE
import com.example.kinopoiskfintech.R
import com.example.kinopoiskfintech.databinding.FragmentMainBinding
import com.example.kinopoiskfintech.presentation.BaseFragment
import com.example.kinopoiskfintech.presentation.mainfragment.listeners.ListItemClickListener
import com.example.kinopoiskfintech.presentation.favoritemovies.FavoriteMoviesFragment
import com.example.kinopoiskfintech.presentation.mainfragment.listeners.LoadingStateListener
import com.example.kinopoiskfintech.presentation.moviedetails.MovieDetailsFragment
import com.example.kinopoiskfintech.presentation.popularmovies.PopularMoviesFragment


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate),
    ListItemClickListener, LoadingStateListener {

    private lateinit var pagerAdapter: MainFragmentPagerAdapter
    private var detailsFragmentContainer: FragmentContainerView? = null


    override fun onStart() {
        super.onStart()
        setupStatusBarColor()
        setStatusBarIconTheme(isDarkTheme = false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsFragmentContainer = binding.detailsFragmentContainer
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
                            }
                        }

                        FavoriteMoviesFragment.FRAGMENT_ID -> {
                            with(binding) {
                                buttonsPanel.favoriteButton.isSelected = true
                                buttonsPanel.popularButton.isSelected = false
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
                binding.fragmentPager.currentItem = PopularMoviesFragment.FRAGMENT_ID
            }
            favoriteButton.setOnClickListener {
                binding.fragmentPager.currentItem = FavoriteMoviesFragment.FRAGMENT_ID
            }
        }
    }
    override fun onMovieClick(movieId: Int) {
        if(detailsFragmentContainer == null){
            findNavController().navigate(MainFragmentDirections.actionMovieDetails(movieId))
        }else{
            launchFragment(
                MovieDetailsFragment.newInstance(movieId)
            )
        }
    }

    private fun launchFragment(fragment: Fragment){
        childFragmentManager.beginTransaction()
            .replace(R.id.details_fragment_container, fragment)
            .commit()
    }

    override fun onLoadingStart() {
        with(binding){
            progressBar.visibility = VISIBLE
            fragmentPager.visibility = GONE
            buttonsPanel.buttonsPanelContainer.visibility = GONE
            binding.fragmentPager.alpha = 0f
            binding.buttonsPanel.buttonsPanelContainer.alpha = 0f
        }
    }

    override fun onLoadingEnd() {
        with(binding){
            progressBar.visibility = GONE
            fragmentPager.visibility = VISIBLE
            buttonsPanel.buttonsPanelContainer.visibility = VISIBLE
            binding.fragmentPager
                .animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null)
            binding.buttonsPanel.buttonsPanelContainer
                .animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null)
        }
    }

}