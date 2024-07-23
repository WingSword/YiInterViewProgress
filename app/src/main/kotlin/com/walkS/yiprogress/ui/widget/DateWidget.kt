package com.walkS.yiprogress.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.walkS.yiprogress.utils.DateTimeUtils
import java.time.Instant

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

/**
 * Project YiProgress
 * Created by Wing on 2024/7/23 10:48.
 * Description:
 *
 **/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YiDatePicker() {
    val context = LocalContext.current
    var showDateTimePicker by remember { mutableStateOf(false) }
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    var time = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showDateTimePicker = true }) {
            Text("Pick Date & Time")
        }

        if (showDateTimePicker) {
            AdvancedTimePickerExample(onConfirm = {
                time.value = "${it.hour}:${it.minute}"
                showDateTimePicker = false
            }) {
                showDateTimePicker = false
            }
        }

        Text(text = "select--${time.value}")
        // Text("Selected: ${selectedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}")
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedTimePickerExample(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentTime.timeInMillis,
    )

    // 封装showDial状态和切换逻辑
    var showDial by remember { mutableStateOf(true) }
    val toggleShowDial = { showDial = !showDial }
    val dateTimeState= ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: currentTime.timeInMillis),
        ZoneId.systemDefault()
    ).format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault())
    ) +" ${timePickerState.hour}"+":${timePickerState.minute}"
    AdvancedTimePickerDialog(
        onDismiss = onDismiss,
        onConfirm = {
            if (showDial) {
                toggleShowDial()
            } else {
                onConfirm(timePickerState)
            }
        },
        toggle = {
            TextButton(onClick = toggleShowDial) {
                Text(
                    if (showDial) "选择时间" else "选择日期"
                )
            }
        },
        title = "Selected: $dateTimeState ",
        timeArea = {
            if (showDial) {
                DatePicker(state = datePickerState)
            } else {
                TimePicker(state = timePickerState)
            }
        }
    )
}

@Composable
fun AdvancedTimePickerDialog(
    title: String = "Select Time",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    timeArea: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                timeArea()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onDismiss) { Text("取消") }
                    TextButton(onClick = onConfirm) { Text("确定") }
                }
            }
        }
    }
}

// 假设 DateTimeUtils 类如下，为了演示添加 try-catch 来处理潜在的异常。
object DateTimeUtils {
    @Throws(Exception::class)
    fun timestampToDateTime(timestamp: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar
    }
}
