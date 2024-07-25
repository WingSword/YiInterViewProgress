package com.walkS.yiprogress.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.intent.MainIntent
import com.walkS.yiprogress.intent.OfferIntent
import com.walkS.yiprogress.state.OfferStateList
import com.walkS.yiprogress.ui.screen.homescreen.HomeInterviewList
import com.walkS.yiprogress.ui.screen.homescreen.InterViewListItem
import kotlinx.coroutines.launch

/**
 *   Created by Administrator on 2024/7/7.
 *   Description:
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OfferListPage(viewModel: MainViewModel) {
    val refreshScope = rememberCoroutineScope()
    val state = viewModel.offerListState.collectAsState().value

    //在这里做网络耗时操作
    fun refresh() = refreshScope.launch {
        viewModel.handleOfferIntent(OfferIntent.fetchOfferList)
    }

    val pullState = rememberPullRefreshState(state.isRefreshing, ::refresh)
    Box(Modifier.pullRefresh(pullState)) {
        if (state.isRefreshing) {
            IndeterminateLinearIndicator()
        } else {
            LazyColumn {
                items(state.list.size) { pos ->
                    Text(text = state.list[pos].companyName)
                }
                if (state.list.isEmpty()) {
                    item {
                        EmptyListWidget()
                    }
                }
            }
        }
        PullRefreshIndicator(state.isRefreshing, pullState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun MinePage() {

    //

}