package com.walkS.yiprogress.ui.field

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

/**
 * Project YiProgress
 * Created by Wing on 2024/8/1 15:22.
 * Description:
 *
 **/

class DropDownField(
    override val name: String,
    override val label: String,
    override var fieldWeight: Float = 0f,
    private vararg val options: String
) : Field {
    private var selectValue = mutableStateOf(options[0])

    @Composable
    override fun Content() {
        var expanded by remember { mutableStateOf(false) }

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "$label: ${selectValue.value}")
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { optionText ->
                    DropdownMenuItem(text = { Text(text = optionText) }, onClick = {
                        selectValue.value = optionText
                        expanded = false
                    })
                }
            }
        }
    }

    override fun getValue(): Any {
        return selectValue.value
    }

    override fun validate(): Boolean {
        return true
    }

}