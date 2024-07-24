package com.walkS.yiprogress.ui.widget

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.state.OfferStateList
import com.walkS.yiprogress.ui.screen.homescreen.InterViewListItem

/**
 *   Created by Administrator on 2024/7/7.
 *   Description:
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferListPage(viewModel: MainViewModel) {
    val state=viewModel.offerListState.collectAsState().value
    val navi = rememberNavController()
    if (state.isRefreshing) {
        IndeterminateLinearIndicator()
    } else {
        LazyColumn {
            items(state.list.size) {pos->
                Text(text = state.list[pos].companyName)
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
fun MinePage() {

    //

}