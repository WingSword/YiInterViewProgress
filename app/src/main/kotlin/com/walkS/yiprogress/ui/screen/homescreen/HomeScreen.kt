package com.walkS.yiprogress.ui.screen.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.blankj.utilcode.util.LogUtils
import com.walkS.yiprogress.InterViewProgress
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.PullToRefreshScreen
import com.walkS.yiprogress.entry.HOME
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.state.InterViewStateList
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.ui.widget.BottomSheet
import com.walkS.yiprogress.ui.widget.IndeterminateLinearIndicator


@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val stateList by viewModel.listState.collectAsState()
    PullToRefreshScreen(viewModel, stateList)
}

fun isHomeScreenPage(route: String?): Boolean {
    return route?.startsWith(HOME) ?: true // 替换为你的二级页面的route
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InterViewListItem(
    state: InterviewState,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color(0xffd6e6f2))
            .padding(8.dp)
            .combinedClickable(
                onClick = {
                    onClick?.invoke()
                },
                onLongClick = {
                    onLongClick?.invoke()
                })
    ) {
        Row(modifier = Modifier.padding(bottom = 4.dp)) {
            Text(
                text = state.companyName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = state.department ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Row {
            Text(text = state.job ?: "", style = MaterialTheme.typography.bodyMedium)
            Text(text = state.time ?: "", style = MaterialTheme.typography.labelMedium)
        }

        InterViewProgress(state)

    }
}


@Composable
fun HomeInterviewList(state: InterViewStateList, viewModel: MainViewModel) {

    if (state.isFreshing) {
        IndeterminateLinearIndicator()
    } else if (state.list.isEmpty()) {
        BottomSheet()
    } else {
        for (item in state.list) {
            LogUtils.d("item:${item.companyName}")
            LazyColumn {
                item {
                    InterViewListItem(item, onClick = {
                        viewModel.navi?.navigate(route = Profile.DETAIL_INTERVIEW.route)
                    })
                }

            }

        }
    }
}


@Composable
fun InterviewCard(viewModel: MainViewModel) {

}

