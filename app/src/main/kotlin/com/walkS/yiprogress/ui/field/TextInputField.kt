package com.walkS.yiprogress.ui.field

import android.util.Patterns
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.walkS.yiprogress.ui.widget.SingleInterViewInput
import java.util.regex.Pattern

class TextInputField(
    override val name: String,
    override val label: String = name,
    override var fieldWeight: Float = 0f,
    val validators: List<Validator>,
    private val keyboardType: KeyboardType = KeyboardType.Text,
    private val inputLines: Int = 1
) : Field {
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
    override fun Content() {
        val isRequired = validators.contains(Required)
        SingleInterViewInput(
            modifier = Modifier.padding(vertical = 8.dp),
            lbl = lbl,
            text = text,
            onValueChange = { newText ->
                text = newText
                hideError()
            },
            hasError = hasError,
            keyboardType = keyboardType,
            inputLines =  inputLines,
            isRequired = isRequired
        )
    }

    override fun getValue(): Any {
        return text
    }

    override fun validate(): Boolean {
        val errors = validators.mapNotNull { validator ->
            when (validator) {
                is Email -> if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    showError(validator.message)
                    validator.message
                } else null

                is Required -> if (text.isEmpty()) {
                    showError(validator.message)
                    validator.message
                } else null

                is Regex -> if (!Pattern.matches(validator.regex, text)) {
                    showError(validator.message)
                    validator.message
                } else null

                else -> null
            }
        }
        if (errors.isNotEmpty()) {
            showError(errors.first()) // 只显示第一个错误
        } else {
            hideError()
        }
        return errors.isEmpty()
    }
}