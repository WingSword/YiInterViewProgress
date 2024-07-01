package com.walkS.yiprogress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.state.InterViewStateList
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.ui.screen.detailscreen.InterViewScreen
import com.walkS.yiprogress.ui.screen.homescreen.HomeList
import com.walkS.yiprogress.ui.screen.homescreen.HomeScreen
import com.walkS.yiprogress.ui.theme.YiProgressTheme
import com.walkS.yiprogress.ui.widget.TopBar
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    // 初始化ViewModel
    private val viewmodel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YiProgressTheme {
                Navi(viewmodel)
            }
        }

    }
}


@Composable
fun Navi(viewModel: MainViewModel) {
    val navController = rememberNavController()
    viewModel.navi = navController
    TopBar(navi = navController) {
        NavHost(navController, startDestination = Profile.HOME.route) {

            composable(route = Profile.HOME.route) {
                HomeScreen(viewModel)
            }

            composable(route = Profile.INTERVIEW.route) {
                InterViewScreen(viewModel)
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
        HomeList(state = stateList,viewModel)
        PullRefreshIndicator(refreshState, pullState, Modifier.align(Alignment.TopCenter))
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
