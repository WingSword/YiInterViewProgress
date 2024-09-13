package com.walkS.yiprogress.ui.widget


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.intent.InterViewIntent
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.ui.digitalkeyboard.DigitalKeyboardConfirmOptions
import com.walkS.yiprogress.ui.digitalkeyboard.WeDigitalKeyboard
import com.walkS.yiprogress.ui.widget.button.CommonTextButton
import com.walkS.yiprogress.ui.widget.picker.DateType
import com.walkS.yiprogress.ui.widget.picker.TimeType
import com.walkS.yiprogress.ui.widget.picker.WeDatePicker
import com.walkS.yiprogress.ui.widget.picker.WeTimePicker
import com.walkS.yiprogress.ui.widget.steps.HorizontalTextLabelStep
import com.walkS.yiprogress.utils.clickableWithoutRipple
import java.time.LocalDate
import java.time.LocalTime

/**
 * Project YiProgress
 * Created by Wing on 2024/7/17 11:30.
 * Description:
 *
 **/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInterView(
    vm: MainViewModel,
    interviewState: InterviewState,
    selectInterViewState: MutableState<String>
) {

    var salary = mutableStateOf("")
    val commitState = vm.isInterviewEditSave.collectAsState()
    val isCommit = commitState.value == -1



    val options = listOf("待面试", "已面试", "已通过", "未通过")
    val selectedIndex = mutableStateOf(0)
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showCityPicker by remember { mutableStateOf(false) }
    var showSalaryUnit by remember { mutableStateOf("K") }
    var showDigitalKeyBoard by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    var interviewNote by remember { mutableStateOf("") }
    var selectedTime = remember { LocalTime.now() }
    var selectedDate = remember { LocalDate.now() }
    var selectedDateText = remember { "选择面试日期" }
    var selectedTimeText = remember { "选择面试时间" }
    Surface(modifier = Modifier
        .fillMaxSize()
        .clickableWithoutRipple {
            // 请求焦点
            if (showDigitalKeyBoard) {
                focusManager.clearFocus()
            }
        }) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
        ) {

            item {
                SingleInterViewInput(
                    interviewState.companyName,
                    lbl = "公司",
                    isRequired = true,
                    isCommit = isCommit,
                ) {
                    vm.handleInterViewIntent(InterViewIntent.InterviewDataChanged("公司", it))
                }
            }
            item {
                SingleInterViewInput(
                    interviewState.job,
                    lbl = "岗位",
                    isRequired = true,
                    isCommit = isCommit
                ) {
                    vm.handleInterViewIntent(InterViewIntent.InterviewDataChanged("岗位", it))
                }
            }
            item {
                SingleInterViewInput(
                    interviewState.department,
                    lbl = "部门",
                    isRequired = false,
                    isCommit = isCommit,
                ) {
                    vm.handleInterViewIntent(InterViewIntent.InterviewDataChanged("部门", it))
                }
            }
            item {
                Row {
                    SingleInterViewInput(
                        interviewState.salary.toString(),
                        lbl = "薪资",
                        isRequired = false,
                        isCommit = isCommit,
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged {
                                showDigitalKeyBoard = it.isFocused
                            },
                        readOnly = true,
                        trailingIcon = {
                            TextButton(
                                modifier = Modifier.size(36.dp),
                                shape = CircleShape,
                                onClick = {
                                    if (showDigitalKeyBoard) {
                                        focusManager.clearFocus()
                                    }
                                    showSalaryUnit = if (showSalaryUnit == "K") {
                                        "W"
                                    } else {
                                        "K"
                                    }
                                    vm.handleInterViewIntent(
                                        InterViewIntent.InterviewDataChanged(
                                            "薪资单位",
                                            showSalaryUnit
                                        )
                                    )
                                },
                                border = BorderStroke(
                                    2.dp,
                                    if (showDigitalKeyBoard) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
                                ),
                            ) {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = showSalaryUnit,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.SansSerif
                                )
                            }
                        }
                    ) {

                    }
                    SingleInterViewInput(
                        modifier = Modifier.weight(1f),
                        text = interviewState.city,
                        lbl = "工作城市",
                        isRequired = false,
                        isCommit = isCommit
                    ) {
                        vm.handleInterViewIntent(
                            InterViewIntent.InterviewDataChanged(
                                "城市",
                                it
                            )
                        )
                    }
                }
            }
            item {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .background(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                            .fillParentMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        HorizontalTextLabelStep(options, selectedIndex.value, onItemClick = {
                            selectedIndex.value = it
                            if (showDigitalKeyBoard) {
                                focusManager.clearFocus()
                            }
                        })
                    }
                    AnimatedVisibility(options[selectedIndex.value].contains("面试")) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CommonTextButton(
                                    selectedDateText,
                                    onClick = { showDatePicker = true },
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                )

                                CommonTextButton(
                                    selectedTimeText,
                                    onClick = { showTimePicker = true },
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                )
                            }
                            AnimatedVisibility(options[selectedIndex.value] == "待面试") {
                                SingleInterViewInput(
                                    interviewState.department,
                                    lbl = "面试地点",
                                    isRequired = false,
                                    isCommit = isCommit,
                                ) {
                                    InterViewIntent.InterviewDataChanged(
                                        "面试地点",
                                        it
                                    )
                                }
                            }
                        }
                    }
                    AnimatedVisibility(options[selectedIndex.value] != ("待面试")) {
                        WeTextarea(
                            value = interviewNote, label = "面试摘要", onChange = {
                                interviewNote = it
                                InterViewIntent.InterviewDataChanged(
                                    "备注",
                                    it
                                )
                            }, modifier = Modifier
                                .padding(vertical = 16.dp)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    shape = MaterialTheme.shapes.large
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }


            }

        }
        WeDatePicker(
            visible = showDatePicker,
            value = selectedDate,
            type = DateType.DAY,
            onCancel = { showDatePicker = false }) {
            selectedDate = it
            selectedDateText = it.toString()
        }
        WeTimePicker(
            visible = showTimePicker,
            value = selectedTime,
            type = TimeType.MINUTE,
            onCancel = { showTimePicker = false },
            onChange = {
                selectedTime = it
                selectedTimeText = it.toString()
            })

        WeDigitalKeyboard(
            visible = showDigitalKeyBoard,
            value = salary.value,
            onHide = {
                if (showDigitalKeyBoard) {
                    focusManager.clearFocus()
                }
            },
            confirmButtonOptions = DigitalKeyboardConfirmOptions(
                text = "确定",
                color = MaterialTheme.colorScheme.primaryContainer
            ),
            onConfirm = {
                showDigitalKeyBoard = false

            }) { salary.value = it }

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


@Composable
fun SingleInterViewInput(
    text: String?,
    modifier: Modifier = Modifier.fillMaxWidth(),
    keyboardType: KeyboardType = KeyboardType.Text,
    hasError: Boolean = false,
    inputLines: Int = 1,
    lbl: String = "",
    isRequired: Boolean = false,
    isCommit: Boolean = false,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text ?: "",
        onValueChange = {
            onValueChange(it)
        },
        isError = isCommit && (hasError || (isRequired && text.isNullOrBlank())),
        maxLines = inputLines,
        textStyle = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
        label = {
            Text(
                buildString {
                    append(lbl)
                    if (isRequired) append(" *") // 显示必填字段标识
                }, style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        },
        readOnly = readOnly,
        singleLine = inputLines == 1,
        modifier = modifier.padding(horizontal = 4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorLabelColor = MaterialTheme.colorScheme.error,
        ),
        shape = CircleShape,

        )
}

@Preview(backgroundColor = 0xffffffff)
@Composable
fun InputPreview() {

}
