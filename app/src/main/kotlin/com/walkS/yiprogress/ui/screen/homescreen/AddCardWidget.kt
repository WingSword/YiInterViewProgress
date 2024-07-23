package com.walkS.yiprogress.ui.screen.homescreen

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AddCircle
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.Button

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.ui.theme.ChineseColor
import com.walkS.yiprogress.ui.theme.Morandi
import com.walkS.yiprogress.ui.widget.AdvancedTimePickerExample
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

/**
 * Project YiProgress
 * Created by Wing on 2024/7/17 11:30.
 * Description:
 *
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInterView() {
    val brush = remember {
        Brush.linearGradient(
            colors = listOf(
                Morandi.MorandiRed,
                Morandi.MorandiBlue,
                Morandi.MorandiPink,
                Morandi.MorandiBrown,
                Morandi.MorandiGreen
            )
        )
    }
    var companyName by remember { mutableStateOf("") }
    var departName by remember { mutableStateOf("") }
    var jobName by remember { mutableStateOf("") }
    var num by remember { mutableIntStateOf(3) }

    Column {
        Row() {
            OutlinedTextField(
                value = companyName, onValueChange = { companyName = it },
                textStyle = TextStyle(brush = brush),
                label = { Text("公司") },
                singleLine = true,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f)
            )
            OutlinedTextField(
                value = departName, onValueChange = { departName = it },
                textStyle = TextStyle(brush = brush),
                label = { Text("部门") },
                singleLine = true,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = jobName, onValueChange = { jobName = it },
                textStyle = TextStyle(brush = brush),
                label = { Text("岗位") },
                singleLine = true,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(2f)
            )

        }
        val timeMap = remember {
            mutableStateMapOf<String,String>()
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(num) { no ->

            }
            item {
                IconButton(
                    onClick = { num-- },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(brush, shape = MaterialTheme.shapes.small)
                        .padding(4.dp)

                ) {
                    Icon(imageVector = Icons.Sharp.AddCircle, contentDescription = "")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterViewTimePicker( no:Int,showDelete: Boolean, onDelete: () -> Unit) {
    var showDateTimePicker by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(40.dp)
            .background(ChineseColor.TuoYan, shape = MaterialTheme.shapes.small)

    ) {
        TextButton(onClick = {
            showDateTimePicker = true
        }) {
            //Text(text = noText[no] ?:"第${no}次面试时间")
        }
        Spacer(modifier = Modifier.weight(1f))
        if (showDelete) {
            IconButton(onClick = { onDelete() }) {
                Icon(imageVector = Icons.Sharp.Delete, contentDescription = "")
            }
        }
    }

    if (showDateTimePicker) {
        AdvancedTimePickerExample(onConfirm = {
            //noText.value = "${it.hour}:${it.minute}"
            showDateTimePicker = false
        }) {
            showDateTimePicker = false
        }
    }
}
