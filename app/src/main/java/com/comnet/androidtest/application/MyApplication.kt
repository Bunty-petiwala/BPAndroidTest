package com.comnet.androidtest.application

import android.app.Application
import android.content.res.Configuration

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}