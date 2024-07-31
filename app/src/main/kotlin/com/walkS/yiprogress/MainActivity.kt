package com.walkS.yiprogress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.walkS.yiprogress.ui.screen.homescreen.App
import com.walkS.yiprogress.ui.theme.YiProgressTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            YiProgressTheme {
                App()
            }
        }
    }
}
