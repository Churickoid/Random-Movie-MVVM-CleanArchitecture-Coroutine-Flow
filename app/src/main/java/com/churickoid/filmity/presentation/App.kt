package com.churickoid.filmity.presentation

import android.app.Application
import com.churickoid.filmity.data.di.DependencyInjectionContainer

class App: Application() {

    lateinit var container: DependencyInjectionContainer
    override fun onCreate() {
        super.onCreate()
        container = DependencyInjectionContainer(applicationContext)
    }
}


