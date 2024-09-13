package com.walkS.yiprogress

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        CoroutineScope(Dispatchers.IO).launch {

        }
    }
}

fun androidx.fragment.app.Fragment.requireContextCompat(): Context {
    return requireContext().applicationContext
}

fun ComponentActivity.requireContextCompat(): Context {
    return applicationContext
}