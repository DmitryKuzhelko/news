package com.trainee.newsapi

import android.app.Application
import com.trainee.newsapi.di.AppComponent
import com.trainee.newsapi.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
    }

}