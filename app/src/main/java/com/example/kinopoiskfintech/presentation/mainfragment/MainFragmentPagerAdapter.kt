package com.example.kinopoiskfintech.presentation.mainfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kinopoiskfintech.presentation.favoritemovies.FavoriteMoviesFragment
import com.example.kinopoiskfintech.presentation.popularmovies.PopularMoviesFragment

class MainFragmentPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = TABS_COUNT

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            PopularMoviesFragment()
        }else FavoriteMoviesFragment()
    }

    companion object {
        private const val TABS_COUNT = 2
    }
}