package com.example.randommovie.presentation

import android.app.Application
import com.example.randommovie.data.di.DependencyInjectionContainer

class App: Application() {

    lateinit var container: DependencyInjectionContainer
    override fun onCreate() {
        super.onCreate()
        container = DependencyInjectionContainer(applicationContext)
    }
}


