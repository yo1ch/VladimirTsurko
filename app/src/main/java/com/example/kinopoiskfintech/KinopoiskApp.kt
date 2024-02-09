package com.example.kinopoiskfintech

import android.app.Application
import com.example.kinopoiskfintech.di.DaggerApplicationComponent

class KinopoiskApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}