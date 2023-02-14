package com.example.randommovie.presentation

import android.app.Application
import com.example.randommovie.presentation.di.DIContainer

class App: Application() {
    val container = DIContainer()
}