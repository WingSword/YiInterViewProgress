package com.walkS.yiprogress.ui.widget

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.walkS.yiprogress.state.FormState
import com.walkS.yiprogress.ui.field.Field

@Composable
fun FormRow(vararg fields: @Composable () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        fields.forEachIndexed { index, field ->
            Box(Modifier.weight(1f)) {
                field() // 均分宽度
            }
        }
    }
}

@Composable
fun FieldContent(
    modifier: Modifier = Modifier,
    // 其他参数...
) {
    // TextField code with the provided modifier
}


@Composable
fun Form(state: FormState, fields: List<Field>) {
    state.fields = fields

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)
    ) {
        var rowWeight = 0f
        items(fields.size) {
            rowWeight = fields[it].fieldWeight
            if (rowWeight == 0f) {
                fields[it].Content()
            }
        }
    }
}
   