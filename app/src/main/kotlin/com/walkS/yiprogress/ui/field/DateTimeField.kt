package com.walkS.yiprogress.ui.field

import androidx.compose.foundation.clickable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeField(
    override val name: String,
    override val label: String,
    override val fieldWeight: Float = 0f,
) : Field {
    var dateTime: LocalDateTime by mutableStateOf(LocalDateTime.now())
    var formattedDateTime: String by mutableStateOf(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))

    fun onDateChanged(date: LocalDateTime) {
        dateTime = date
        formattedDateTime = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @Composable
    override fun Content() {
        OutlinedTextField(
            value = formattedDateTime,
            onValueChange = {},
            label = { Text(text = label) },
            readOnly = true,
            modifier = Modifier.clickable {
                // 这里可以弹出日期时间选择器对话框
            }
        )
    }

    override fun getValue(): Any {
        return ""
    }

    override fun validate(): Boolean {
        // 根据需要添加验证逻辑
        return true
    }
}
