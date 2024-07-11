package com.walkS.yiprogress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.state.InterViewStateList
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.ui.screen.detailscreen.DetailScreen
import com.walkS.yiprogress.ui.screen.homescreen.HomeInterviewList
import com.walkS.yiprogress.ui.screen.homescreen.HomeScreen
import com.walkS.yiprogress.ui.theme.YiProgressTheme
import com.walkS.yiprogress.ui.widget.MinePage
import com.walkS.yiprogress.ui.widget.NavigationBottomLayout
import com.walkS.yiprogress.ui.widget.NavigationTopBar
import com.walkS.yiprogress.ui.widget.OfferListPage
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
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullToRefreshScreen(viewModel: MainViewModel, stateList: InterViewStateList) {
    val refreshScope = rememberCoroutineScope()
    val refreshState = stateList.isFreshing

    //在这里做网络耗时操作
    fun refresh() = refreshScope.launch {
        viewModel.handleIntent(MainIntent.FetchDataList)
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
        }
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
        }
    }
}


@Composable
fun InterViewProgress(state: InterviewState) {
    val progressNum = state.progressNum
    val progress = state.progress
    if (progressNum < 1 || progress > progressNum) return
    var sliderPosition by remember { mutableStateOf(progress) }

    Column {
        Text(
            text = state.interviewStatus.toString(),
            modifier = Modifier.align(Alignment.End),
            style = MaterialTheme.typography.labelMedium
        )
        Slider(
            value = sliderPosition.toFloat(),
            onValueChange = { sliderPosition = it.toInt() },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = progressNum,
            valueRange = 0f..progressNum.toFloat(),
            enabled = false
        )
    }
}
