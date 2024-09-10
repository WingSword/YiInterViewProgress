package com.walkS.yiprogress.ui.widget.radio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.walkS.yiprogress.R
import com.walkS.yiprogress.utils.clickableWithoutRipple


@Composable
fun WeRadio(
    label: String,
    description: String? = null,
    checked: Boolean,
    disabled: Boolean,
    onClick: () -> Unit
) {
    Column {
        Row(
            Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickableWithoutRipple(!disabled) {
                    onClick()
                }
                .padding(16.dp)
                .alpha(if (disabled) 0.1f else 1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 17.sp
                )
                description?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 14.sp
                    )
                }
            }
            Icon(
                (Icons.Sharp.Check),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (checked) MaterialTheme.colorScheme.primary else Color.Transparent
            )
        }
        HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp), color = Color.LightGray)
    }
}

@Preview()
@Composable
fun MyRadioPreview(){
    WeRadio(
        label = "123",
        description = "123",
        checked = true,
        disabled = false,
        onClick = {}
    )
}
