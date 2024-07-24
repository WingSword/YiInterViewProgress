package com.walkS.yiprogress.utils

import android.util.Patterns
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.walkS.yiprogress.ui.screen.homescreen.CommonSingleInputText


class Field(val name: String, val label: String = name, val validators: List<Validator>) {
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
        CommonSingleInputText(
            value = text,
            isError = hasError,
            label = lbl,
            modifier = Modifier.padding(10.dp),
            onValueChange = { value ->
                hideError()
                text = value
            }
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
private const val REQUIRED_MESSAGE = "this field is required"
private const val REGEX_MESSAGE = "value does not match the regex"

sealed interface Validator
open class Email(var message: String = EMAIL_MESSAGE) : Validator
open class Required(var message: String = REQUIRED_MESSAGE) : Validator
open class Regex(var message: String, var regex: String = REGEX_MESSAGE) : Validator
