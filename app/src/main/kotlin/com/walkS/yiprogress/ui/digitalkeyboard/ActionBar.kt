package com.walkS.yiprogress.ui.digitalkeyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun ActionBar(
    widthPerItem: Dp,
    confirmButtonOptions: DigitalKeyboardConfirmOptions,
    isEmpty: Boolean,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .width(widthPerItem)
                .height(50.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .clickable {
                    onBack()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                contentDescription = "回退",
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
        Box(
            modifier = Modifier
                .width(widthPerItem)
                .height((50 * 3 + 8 * 2).dp)
                .clip(MaterialTheme.shapes.medium)
                .background(if (isEmpty) confirmButtonOptions.color.copy(0.4f) else confirmButtonOptions.color)
                .clickable(enabled = !isEmpty) { onConfirm() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = confirmButtonOptions.text,
                color = if (isEmpty) MaterialTheme.colorScheme.primary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.primary,
                fontSize = 17.sp
            )
        }
    }
}