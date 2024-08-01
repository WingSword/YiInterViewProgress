package com.walkS.yiprogress.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.intent.InterViewIntent
import com.walkS.yiprogress.state.FormState
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.ui.field.Required
import com.walkS.yiprogress.ui.field.TextInputField
import com.walkS.yiprogress.ui.theme.ChineseColor
import com.walkS.yiprogress.utils.RandomUtils

/**
 * Project YiProgress
 * Created by Wing on 2024/7/17 11:30.
 * Description:
 *
 **/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInterView(vm: MainViewModel) {
    val state by remember { mutableStateOf(FormState()) }
    val formList = listOf(
        TextInputField(name = "companyName", label = "公司", validators = listOf(Required)),
        TextInputField(name = "department", label = "部门", validators = listOf(Required)),
        TextInputField(name = "job", label = "岗位", validators = listOf(Required)),
        TextInputField(name = "city", label = "工作城市", validators = listOf(Required)),
        TextInputField(name = "sal", label = "薪资", validators = listOf(Required)),
    )


    val options = arrayOf("待面试", "已面试", "已通过", "未通过")
    val selectInterViewState = remember {
        mutableStateOf(options[0])
    }
    var showDateTimePicker by remember { mutableStateOf(false) }

    val interviewTime = remember {
        mutableStateOf("")
    }

    Surface(modifier = Modifier.size(300.dp, 500.dp)) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(4.dp)
                .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)
        ) {
            item {
                Text(text = "新增一个面试")
            }
            items(formList.size) {
                formList[it].Content()
            }
            item {
                BaseDropDownMenu(
                    label = "面试状态", selectInterViewState,
                    options = options
                )
                if (selectInterViewState.value == "待面试") {
                    Button(onClick = { showDateTimePicker = true }) {
                        Text("面试时间：" + interviewTime.value)
                    }
                }
            }
        }
    }

    if (showDateTimePicker) {
        AdvancedTimePickerExample(onConfirm = { it, time ->
            interviewTime.value = time
            showDateTimePicker = false
        }) {
            showDateTimePicker = false
        }
    }
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            state.fields = formList
            if (state.validate()) {
                val data=state.getData()
                val interState=InterviewState(
                    itemId = RandomUtils.optInterViewRandomId(),
                    companyName = data.get("companyName").toString() ,
                    department = data.get("department").toString(),
                    job = data.get("job").toString(),
                    city = data.get("city").toString() ,
                    interviewStatus = selectInterViewState.value,
                    interviewTime = interviewTime.value,
                    salary = data.get("sal") as? Double ?:0.0
                )
                vm.handleInterViewIntent(InterViewIntent.NewInterView(interState))
            }
        }) {
        Text("完成")
    }
}

@Composable
fun BaseDropDownMenu(label: String, selectValue: MutableState<String>, vararg options: String) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "$label: ${selectValue.value}")
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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
}

@Composable
fun InterviewDateArea() {

}

@Composable
fun CommonSingleInputText(
    value: String = "",
    label: String = "",
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
) {
    var text by remember { mutableStateOf(value) }
    OutlinedTextField(
        value = text, onValueChange = {
            text = it
            onValueChange(it)
        },
        textStyle = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
        label = { Text(label) },
        singleLine = true,
        modifier = modifier
            .padding(horizontal = 4.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}

