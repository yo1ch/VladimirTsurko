package com.example.kinopoiskfintech.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.kinopoiskfintech.R

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
abstract class BaseFragment<VB: ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun setupStatusBarColor(){
        requireActivity().window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            requireActivity().window.statusBarColor =
                requireContext().getColor(R.color.white)
        }
    }

    open fun setTransluentStatusbar(){
        requireActivity().window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            requireActivity().window.statusBarColor =
                requireContext().getColor(R.color.transparent)
        }
    }
    @Suppress("deprecation")
    open fun setStatusBarIconTheme(isDarkTheme: Boolean): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val oldValue = requireActivity().window.decorView.windowInsetsController?.systemBarsAppearance
            val lightFlag = WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            val flag = if (isDarkTheme) 0 else lightFlag
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(flag, lightFlag)
            oldValue?.let { oldValue and lightFlag == 0 } ?: true
        } else {
            val oldValue = requireActivity().window.decorView.systemUiVisibility
            requireActivity().window.decorView.systemUiVisibility =
                if (isDarkTheme) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            oldValue and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR == 0
        }
}