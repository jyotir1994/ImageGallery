package com.example.imagegallery

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseActivity : Application() {

//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(base)
//        MultiDex.install(this)
//    }
}