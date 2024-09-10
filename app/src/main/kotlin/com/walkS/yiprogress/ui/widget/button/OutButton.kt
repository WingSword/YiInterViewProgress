package com.walkS.yiprogress.ui.widget.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CommonTextButton(
    text: String,
    width: Dp = 144.dp,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    fontColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = { }
) {

    TextButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .width(width)
            .wrapContentHeight()
            .padding(4.dp)
            .background(color = color, shape = CircleShape)
            .padding(vertical = 4.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium,color=fontColor)
    }
}