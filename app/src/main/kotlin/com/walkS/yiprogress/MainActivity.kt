package com.walkS.yiprogress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.intent.BottomSheetIntent
import com.walkS.yiprogress.state.InterViewStateList
import com.walkS.yiprogress.ui.screen.detailscreen.DetailScreen
import com.walkS.yiprogress.ui.screen.homescreen.HomeInterviewList
import com.walkS.yiprogress.ui.screen.homescreen.HomeScreen
import com.walkS.yiprogress.ui.theme.YiProgressTheme
import com.walkS.yiprogress.ui.widget.MinePage
import com.walkS.yiprogress.ui.widget.NavigationBottomLayout
import com.walkS.yiprogress.ui.widget.NavigationTopBar
import com.walkS.yiprogress.ui.widget.OfferListPage
import com.walkS.yiprogress.ui.widget.PartialBottomSheet
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    // 初始化ViewModel
    private val viewmodel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            YiProgressTheme {
                AppWithNavigation(viewModel = viewmodel)
            }
        }
    }

    fun initDataBase(){

    }
}






@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullToRefreshScreen(viewModel: MainViewModel, stateList: InterViewStateList) {
    val refreshScope = rememberCoroutineScope()
    val refreshState = stateList.isFreshing

    //在这里做网络耗时操作
    fun refresh() = refreshScope.launch {
        viewModel.handleInterViewList(MainIntent.FetchDataList)
    }

    val pullState = rememberPullRefreshState(refreshState, ::refresh)

    Box(Modifier.pullRefresh(pullState)) {
        HomeInterviewList(state = stateList, viewModel)
        PullRefreshIndicator(refreshState, pullState, Modifier.align(Alignment.TopCenter))
    }

}


@Composable
fun AppWithNavigation(viewModel: MainViewModel) {
    val navi = rememberNavController()
    viewModel.navi = navi
    val currentRoute = navi.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        topBar = {
            NavigationTopBar(navi, currentRoute)
        },
        bottomBar = {
            NavigationBottomLayout(navi, currentRoute)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.handleBottomIntent(BottomSheetIntent.OpenSheet) }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
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
                            Profile.HOME_OFFER_LIST_PAGE -> OfferListPage()
                            Profile.HOME_MINE_PAGE -> MinePage()
                            Profile.DETAIL_INTERVIEW -> DetailScreen(viewModel)
                            else -> {}
                        }
                    }
                }
            }
            PartialBottomSheet(viewModel)
        }

    }
}

