package com.walkS.yiprogress.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


private val YiShape=Shapes(
    small = ShapeDefaults.Small.copy(
        CornerSize(8.dp)
    ),
    medium = ShapeDefaults.Medium.copy(
        CornerSize(16.dp)
    ),
    large = ShapeDefaults.Large.copy(
        CornerSize(24.dp)
    ),
    extraSmall = ShapeDefaults.ExtraSmall.copy(
        CornerSize(4.dp)
    ),
    extraLarge = ShapeDefaults.ExtraLarge.copy(
        CornerSize(32.dp)
    ),
)



@Composable
fun YiProgressTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> Colors.DarkColorScheme
        else -> Colors.LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = YiShape
    )
}