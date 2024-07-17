package com.walkS.yiprogress.ui.screen.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AddCircle
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.walkS.yiprogress.ui.theme.Morandi

/**
 * Project YiProgress
 * Created by Wing on 2024/7/17 11:30.
 * Description:
 *
 **/

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
            Text("预计面试轮数$num", modifier = Modifier.padding(horizontal = 4.dp))
        }
        val lazyState = rememberLazyListState()
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(num) { no ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(brush, shape = MaterialTheme.shapes.small)

                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "${no + 1} 面时间")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if (no == num - 1) {
                        IconButton(onClick = { num-- }) {
                            Icon(imageVector = Icons.Sharp.Delete, contentDescription = "")
                        }
                    }
                }
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

fun datePicker(){

}