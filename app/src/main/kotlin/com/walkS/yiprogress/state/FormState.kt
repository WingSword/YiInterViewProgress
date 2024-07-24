package com.walkS.yiprogress.state

import com.walkS.yiprogress.utils.Field

class FormState{
     var fields: List<Field> = listOf()
         set(value) { field = value }

     fun validate(): Boolean {
         var valid = true
         for (field in fields) if (!field.validate()) {
             valid = false
             break
         }
         return valid
     }

     fun getData(): Map<String, String> = fields.map { it.name to it.text }.toMap()
 }
