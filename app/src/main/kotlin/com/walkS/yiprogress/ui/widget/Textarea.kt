package com.walkS.yiprogress.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeTextarea(
    value: String?,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    disabled: Boolean = false,
    labelWidth: Dp = 68.dp,
    max: Int? = null,
    topBorder: Boolean = false,
    onChange: ((String) -> Unit)? = null
) {
    val localValue = value ?: ""

    Column {
        if (topBorder) {
            HorizontalDivider()
        }
        Row(
            modifier = modifier

        ) {
            if (label?.isNotEmpty() == true) {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 16.sp,
                    modifier = Modifier.width(labelWidth)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            BasicTextField(
                value = localValue,
                onValueChange = { str ->
                    onChange?.apply {
                        if (max != null && str.length > max) {
                            invoke(str.slice(0..<max))
                        } else {
                            invoke(str)
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f),
                readOnly = disabled,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp
                ),
                minLines = 3,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
            ) { innerTextField ->
                Box {
                    innerTextField()

                    if (localValue.isEmpty() && placeholder?.isNotEmpty() == true) {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 16.sp
                        )
                    }

                    max?.let {
                        Text(
                            text = "${localValue.length}/$it",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.BottomEnd)
                        )
                    }
                }
            }
        }

    }
}