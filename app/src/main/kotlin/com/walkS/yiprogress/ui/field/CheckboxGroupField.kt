package com.walkS.yiprogress.ui.field

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class CheckboxGroupField(
    override val name: String,
    override val label: String,
    override val fieldWeight: Float = 0f,
    val options: List<String>,
) : Field {
    var selectedOptions: MutableSet<String> by mutableStateOf(mutableSetOf())

    @Composable
    override fun Content() {
        Column {
            Text(text = label)
            options.forEach { option ->
                Checkbox(
                    checked = selectedOptions.contains(option),
                    onCheckedChange = {
                        if (it) {
                            selectedOptions.add(option)
                        } else {
                            selectedOptions.remove(option)
                        }
                    }
                )
            }
        }
    }

    override fun getValue(): Any {
        return ""
    }

    override fun validate(): Boolean {
        // 根据需要添加验证逻辑
        return true
    }
}
