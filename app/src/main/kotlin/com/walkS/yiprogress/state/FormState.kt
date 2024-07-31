package com.walkS.yiprogress.state

import com.walkS.yiprogress.ui.field.Field
import com.walkS.yiprogress.ui.field.TextInputField

class FormState {
    var fields: List<Field> = listOf()
        set(value) {
            field = value
        }

    fun validate(): Boolean {
        var valid = true
        for (field in fields) if (!field.validate()) {
            valid = false
            break
        }
        return valid
    }

    fun getData(): Map<String, Any> = fields.map { it.name to it.getValue() }.toMap()
}
