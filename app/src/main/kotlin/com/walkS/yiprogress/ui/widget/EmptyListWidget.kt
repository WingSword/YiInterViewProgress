package com.walkS.yiprogress.ui.widget


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.walkS.yiprogress.R

@Composable
@Preview
fun EmptyListWidget() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
         ) {
        Image(
            painter = painterResource(id = R.drawable.empty_data),
            contentDescription = "暂无数据",
            modifier = Modifier
                .padding(32.dp)
                .size(225.dp)
        )
        Text(
            text = stringResource(R.string.list_no_data),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }


}

@Composable
fun NoMoreDataWidget() {
    HorizontalDivider(thickness = 2.dp)
    Box(modifier = Modifier.padding(vertical = 32.dp)) {
        Text(
            text = stringResource(R.string.list_no_data),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}