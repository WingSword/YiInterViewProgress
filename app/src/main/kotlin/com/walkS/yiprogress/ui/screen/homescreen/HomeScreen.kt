package com.walkS.yiprogress.ui.screen.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.MainViewModel.Companion.DIALOG_TYPE_SHOW_ADD_INTERVIEW
import com.walkS.yiprogress.MainViewModel.Companion.DIALOG_TYPE_SHOW_ADD_OFFER
import com.walkS.yiprogress.entry.HOME
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.intent.InterViewIntent
import com.walkS.yiprogress.intent.MainIntent
import com.walkS.yiprogress.intent.OfferIntent
import com.walkS.yiprogress.state.FormState
import com.walkS.yiprogress.state.InterViewStateList
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.ui.screen.detailscreen.DetailScreen
import com.walkS.yiprogress.ui.widget.HomeInterviewList
import com.walkS.yiprogress.ui.widget.MinePage
import com.walkS.yiprogress.ui.widget.NavigationBottomLayout
import com.walkS.yiprogress.ui.widget.NavigationTopBar
import com.walkS.yiprogress.ui.widget.OfferListPage
import com.walkS.yiprogress.ui.widget.OfferPageScreen
import com.walkS.yiprogress.ui.widget.PartialBottomSheet
import com.walkS.yiprogress.ui.widget.TotalDialog
import com.walkS.yiprogress.utils.RandomUtils
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullToRefreshScreen(viewModel: MainViewModel, stateList: InterViewStateList) {
    val refreshScope = rememberCoroutineScope()
    val refreshState = stateList.isFreshing

    //在这里做网络耗时操作
    fun refresh() = refreshScope.launch {
        viewModel.handleInterViewIntent(InterViewIntent.FetchDataList)
    }

    val pullState = rememberPullRefreshState(refreshState, ::refresh)

    Box(Modifier.pullRefresh(pullState)) {
        HomeInterviewList(state = stateList, viewModel)
        PullRefreshIndicator(refreshState, pullState, Modifier.align(Alignment.TopCenter))
    }

}


@Composable
fun App() {
    val viewModel = viewModel<MainViewModel>()
    LaunchedEffect(Unit) {
        viewModel.handleOfferIntent(OfferIntent.fetchOfferList) // 这将在页面首次加载时调用

        viewModel.handleInterViewIntent(InterViewIntent.FetchDataList) // 这将在页面首次加载时调用
    }
    val snackState = viewModel.homeSnackBarHostState.collectAsState()
    val navi = rememberNavController()
    val currentRoute = navi.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        topBar = {
            NavigationTopBar(navi, currentRoute)
        },
        bottomBar = {
            NavigationBottomLayout(navi, currentRoute)
        },

        snackbarHost = {
            SnackbarHost(hostState = snackState.value)
        },
    ) { innerPadding ->
        //content
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            NavHost(navi, startDestination = Profile.HOME_INTERVIEW_LIST_PAGE.route) {
                Profile.entries.forEach { screen ->
                    composable(route = screen.route) {
                        when (screen) {
                            Profile.HOME_INTERVIEW_LIST_PAGE -> HomeScreen(viewModel)
                            Profile.HOME_OFFER_LIST_PAGE -> OfferListPage(viewModel)
                            Profile.HOME_MINE_PAGE -> MinePage()
                            Profile.DETAIL_INTERVIEW -> DetailScreen(viewModel, )
                            Profile.DETAIL_OFFER -> OfferPageScreen(
                                navHostController = navi,
                                viewModel = viewModel
                            )

                            else -> {}
                        }
                        PartialBottomSheet(navi, viewModel)
                        TotalDialog(viewModel)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val stateList by viewModel.listState.collectAsState()
    PullToRefreshScreen(viewModel, stateList)
}

fun isHomeScreenPage(route: String?): Boolean {
    return route?.startsWith(HOME) ?: true // 替换为你的二级页面的route
}


