package com.example.randommovie.presentation

import android.app.Application
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import com.example.randommovie.data.di.DependencyInjectionContainer

class App: Application() {

    lateinit var container: DependencyInjectionContainer
    override fun onCreate() {
        super.onCreate()
        container = DependencyInjectionContainer(applicationContext)
    }
}


