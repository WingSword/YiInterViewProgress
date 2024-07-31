package com.walkS.yiprogress.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.state.InterViewStateList
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.ui.theme.ChineseColor
import com.walkS.yiprogress.ui.theme.Morandi

/**
 * Project YiProgress
 * Created by Wing on 2024/7/26 14:59.
 * Description:
 *
 **/


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InterViewListItem(
    state: InterviewState,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .combinedClickable(
                onClick = {
                    onClick?.invoke()
                },
                onLongClick = {
                    onLongClick?.invoke()
                })
    ) {
        InterviewCard(state = state)
    }
}


@Composable
fun HomeInterviewList(state: InterViewStateList, viewModel: MainViewModel) {
    val navi = rememberNavController()
    if (state.isFreshing) {
        IndeterminateLinearIndicator()
    } else {
        LazyColumn {
            items(state.list.size) {pos->
                InterViewListItem(state.list[pos], onClick = {

                    navi.navigate(route = Profile.DETAIL_INTERVIEW.route)
                })
            }
            if(state.list.isEmpty()){
                item {
                    EmptyListWidget()
                }
            }
        }

    }
}

@Composable
fun InterviewCard(state: InterviewState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .background(color = Morandi.MorandiPurple, shape = MaterialTheme.shapes.large)
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Row(modifier = Modifier.padding(bottom = 4.dp)) {
            Text(
                text = state.companyName + state.department,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.CenterVertically)
            )

        }

        Text(text = state.job ?: "", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = state.time ?: "", style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = state.interviewStatus.toString(),
                style = MaterialTheme.typography.labelMedium
            )
        }
        LazyRow {
            items(state.progressNum) { pos ->
                val color =
                    if (pos == state.progress) ChineseColor.Su
                    else if (pos < state.progress) ChineseColor.BiSe
                    else ChineseColor.ChongSe
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(12.dp)
                        .background(color = color, shape = CircleShape)
                )

            }
        }
    }

}


@Preview
@Composable
fun interviewPreview() {
    val state = InterviewState(
        itemId = 1,
        companyName = "一二四",
        department = "三三三",
        job = "散散",
        interviewStatus = "面试中",
        progress = 1,
        progressNum = 5,
        time = "2023-05-01 13:00",
        info = "面试中"
    )
    InterviewCard(state = state)
}