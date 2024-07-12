package com.walkS.yiprogress

import android.app.Application
import android.app.Fragment
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.core.content.ContentProviderCompat.requireContext

/**
 * Project YiProgress
 * Created by Wing on 2024/7/12 15:09.
 * Description:
 *
 **/
class MainApplication : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
    }
}

fun androidx.fragment.app.Fragment.requireContextCompat(): Context {
    return requireContext().applicationContext
}

fun ComponentActivity.requireContextCompat(): Context {
    return applicationContext
}