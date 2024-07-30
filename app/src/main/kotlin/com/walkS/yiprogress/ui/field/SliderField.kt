package com.walkS.yiprogress.ui.field

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

class SliderField(
    override val name: String,
    override val label: String,
    override val fieldWeight: Float = 0f,
    val valueRange: ClosedFloatingPointRange<Float>,
    val initialValue: Float
) : Field {
    var value: Float by mutableStateOf(initialValue)

    @Composable
    override fun Content() {
        Column {
            Text(text = label)
            Slider(
                value = value,
                onValueChange = { newValue ->
                    value = newValue
                },
                valueRange = valueRange,
                steps = 100, // 可以根据需要调整步长
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = value.toString())
        }
    }

    override fun getValue(): Any {
        return value
    }

    override fun validate(): Boolean {
        // 根据需要添加验证逻辑
        return true
    }
}
