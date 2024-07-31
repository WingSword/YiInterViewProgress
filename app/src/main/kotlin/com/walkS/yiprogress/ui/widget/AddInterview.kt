package com.walkS.yiprogress.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Delete

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.intent.InterViewIntent
import com.walkS.yiprogress.state.FormState
import com.walkS.yiprogress.ui.field.Required
import com.walkS.yiprogress.ui.field.TextInputField

import com.walkS.yiprogress.ui.theme.ChineseColor

/**
 * Project YiProgress
 * Created by Wing on 2024/7/17 11:30.
 * Description:
 *
 **/


@Composable
fun AddInterView(vm: MainViewModel) {
    val state by remember { mutableStateOf(FormState()) }
    val formList = listOf(
        TextInputField(name = "companyName", label = "公司", validators = listOf(Required)),
        TextInputField(name = "department", label = "部门", validators = listOf(Required)),
        TextInputField(name = "job", label = "岗位", validators = listOf(Required)),
        TextInputField(name = "job", label = "工作城市", validators = listOf(Required)),

        )

    Surface(modifier = Modifier.size(300.dp, 500.dp)) {
        Form(state = state, fields = formList)
    }
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            if (state.validate()) {
                vm.handleInterViewIntent(InterViewIntent.NewInterView(state))
            }
        }) {
        Text("完成")
    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterViewTimePicker(no: Int, showDelete: Boolean, onDelete: () -> Unit) {
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
