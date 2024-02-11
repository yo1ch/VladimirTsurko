package com.example.kinopoiskfintech.utils

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun SearchView.getQueryChangeFlow(): StateFlow<String>{
    val query = MutableStateFlow(String())

    setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            query.value = newText ?: String()
            return false
        }
    })
    return query
}