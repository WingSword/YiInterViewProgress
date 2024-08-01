package com.walkS.yiprogress.ui.field

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.walkS.yiprogress.ui.widget.AdvancedTimePickerExample
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeField(
    override val name: String,
    override val label: String,
    override var fieldWeight: Float = 0f,
) : Field {
    var dateTime: LocalDateTime by mutableStateOf(LocalDateTime.now())
    var formattedDateTime: String by mutableStateOf(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))

    fun onDateChanged(date: LocalDateTime) {
        dateTime = date
        formattedDateTime = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var showDateTimePicker by remember { mutableStateOf(false) }
        var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }

        var time = remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = formattedDateTime,
            onValueChange = {},
            label = { Text(text = label) },
            readOnly = true,
            modifier = Modifier.clickable {
                showDateTimePicker = true
            }
        )
        if (showDateTimePicker) {
            AdvancedTimePickerExample(onConfirm = {it,time1->
                time.value = "${it.hour}:${it.minute}"
                showDateTimePicker = false
            }) {
                showDateTimePicker = false
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
