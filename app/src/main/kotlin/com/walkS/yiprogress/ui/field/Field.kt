package com.walkS.yiprogress.ui.field

import androidx.compose.runtime.Composable

interface Field {
    val name: String
    val label: String
    val fieldWeight: Float

    @Composable
    fun Content()
    fun getValue(): Any
    fun validate(): Boolean
}

private const val EMAIL_MESSAGE = "invalid email address"
private const val REQUIRED_MESSAGE = "不可为空"
private const val REGEX_MESSAGE = "value does not match the regex"

sealed interface Validator {
    val message: String
}

data object Email : Validator {
    override val message = EMAIL_MESSAGE
}

data object Required : Validator {
    override val message = REQUIRED_MESSAGE
}

data class Regex(val regex: String, override val message: String = REGEX_MESSAGE) : Validator

