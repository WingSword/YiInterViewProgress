package com.walkS.yiprogress.utils

import android.util.Patterns
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.walkS.yiprogress.ui.widget.CommonSingleInputText


class Field(
    val name: String,
    val label: String = name,
    val validators: List<Validator>,
    private val keyboardType: KeyboardType = KeyboardType.Text,
    private val inputLines: Int = 1
) {
    var text: String by mutableStateOf("")
    var lbl: String by mutableStateOf(label)
    var hasError: Boolean by mutableStateOf(false)

    fun clear() {
        text = ""
    }

    private fun showError(error: String) {
        hasError = true
        lbl = error
    }

    private fun hideError() {
        lbl = label
        hasError = false
    }

    @Composable
    fun Content() {
        OutlinedTextField(
            value = text,
            onValueChange = {
                hideError()
                text = it
            },
            isError = hasError,
            maxLines = inputLines,
            textStyle = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
            label = { Text(lbl) },
            singleLine = true,
            modifier = Modifier
                .padding(horizontal = 4.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error,
            ),
        )
    }

    fun validate(): Boolean {
        return validators.map {
            when (it) {
                is Email -> {
                    if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                        showError(it.message)
                        return@map false
                    }
                    true
                }

                is Required -> {
                    if (text.isEmpty()) {
                        showError(it.message)
                        return@map false
                    }
                    true
                }

                is Regex -> {
                    if (!it.regex.toRegex().containsMatchIn(text)) {
                        showError(it.message)
                        return@map false
                    }
                    true
                }

                else -> {
                    true
                }
            }
        }.all { it }
    }

}

private const val EMAIL_MESSAGE = "invalid email address"
private const val REQUIRED_MESSAGE = "不可为空"
private const val REGEX_MESSAGE = "value does not match the regex"

sealed interface Validator
open class Email(var message: String = EMAIL_MESSAGE) : Validator
open class Required(var message: String = REQUIRED_MESSAGE) : Validator
open class Regex(var message: String, var regex: String = REGEX_MESSAGE) : Validator
