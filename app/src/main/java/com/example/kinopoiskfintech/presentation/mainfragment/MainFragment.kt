package com.example.kinopoiskfintech.presentation.mainfragment

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.kinopoiskfintech.databinding.FragmentMainBinding
import com.example.kinopoiskfintech.presentation.BaseFragment


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var pagerAdapter: MainFragmentPagerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        pagerAdapter = MainFragmentPagerAdapter(childFragmentManager, lifecycle)
        binding.fragmentPager.adapter = pagerAdapter
        binding.fragmentPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){

        })
        setupToggleButtons()
    }

    private fun setPopularButtonClick() {
        with(binding) {
            buttonsPanel.popularButton.setOnClickListener {
                it.isSelected = true
                buttonsPanel.favoriteButton.isSelected = false
                fragmentPager.currentItem = 0
            }
        }
    }
    private fun setFavouriteButtonClick() {
        with(binding) {
            buttonsPanel.favoriteButton.setOnClickListener {
                it.isSelected = true
                buttonsPanel.popularButton.isSelected = false
                fragmentPager.currentItem = 1
            }
        }
    }
    private fun checkCurrentPressedButton() {
        with(binding) {
            if (fragmentPager.currentItem == 0) {
                buttonsPanel.favoriteButton.isSelected = true
            } else buttonsPanel.popularButton.isSelected = true
        }
    }
    private fun setupToggleButtons(){
        checkCurrentPressedButton()
        setPopularButtonClick()
        setFavouriteButtonClick()
    }

}