package com.example.randommovie.presentation

import android.app.Application
import com.example.randommovie.presentation.di.DependencyInjectionContainer

class App: Application() {
    val container = DependencyInjectionContainer()
}