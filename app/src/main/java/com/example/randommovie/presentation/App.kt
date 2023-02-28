package com.example.randommovie.presentation

import android.app.Application
import com.example.randommovie.data.di.DependencyInjectionContainer

class App: Application() {
    val container = DependencyInjectionContainer()
}