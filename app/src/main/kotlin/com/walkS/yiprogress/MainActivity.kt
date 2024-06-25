package com.walkS.yiprogress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.blankj.utilcode.util.LogUtils
import com.walkS.yiprogress.state.InterViewStateList
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.ui.theme.YiProgressTheme

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
    TopBar(navi = navController) {
        NavHost(navController, startDestination = Profile.HOME.route) {

            composable(route = Profile.HOME.route) {
                HomeScreen(
                    onNavigateToFriendsList = {
                        navController.navigate(route = Profile.INTERVIEW.route)
                    }, viewModel
                )


            }

            composable(route = Profile.INTERVIEW.route) {

            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navi: NavController, content: @Composable () -> Unit) {
    val model = viewModel(modelClass = MainViewModel::class.java)
    val currentRoute = navi.currentBackStackEntryAsState().value?.destination?.route
    val title = Profile.fromRoute(currentRoute)?.title ?: ""
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(title, modifier = Modifier.clickable {
                        model.handleIntent(MainIntent.FetchDataList)
                    })
                },
                navigationIcon = {
                    IconButton(onClick = { navi.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        //content
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun TopIcon(navi: NavController) {

}


@Composable
fun HomeScreen(onNavigateToFriendsList: () -> Unit, viewModel: MainViewModel) {
//    val viewModel = viewModel(modelClass = MainViewModel::class.java)
    val state by viewModel.listState.collectAsState()
    LogUtils.d("item:${state.list.size}")
    HomeList(state)

}

@Composable
fun InterViewListItem(state: InterviewState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color(0xffd6e6f2))
            .padding(8.dp)
    ) {
        Text(text = state.title, style = MaterialTheme.typography.titleMedium)
        Text(text = state.desc ?: "", style = MaterialTheme.typography.bodyMedium)

    }
}

@Composable
fun InterViewProgress(progress: Int = 0, target: Int = 3) {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeList(state: InterViewStateList) {
    if (state.loading) {
        IndeterminateLinearIndicator()
    } else if (state.list.isEmpty()) {
        BottomSheet()
    } else {
        for (item in state.list) {
            LogUtils.d("item:${item.title}")
            LazyColumn {
                item {
                    InterViewListItem(item)
                }

            }

        }
    }
}

@Composable
fun LoadingCircle() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        ), label = ""
    )
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_loading),
            contentDescription = "",
            modifier = Modifier
                .graphicsLayer { rotationZ = angle }
                .size(30.dp)
        )
    }
}

@Composable
fun BottomSheet() {
    HorizontalDivider(thickness = 2.dp)
    Text(
        text = stringResource(R.string.list_no_data),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    )
}

@Composable
fun IndeterminateLinearIndicator() {
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
fun IndeterminateCircularIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
fun InterViewListScreen(onNavigateToProfile: () -> Unit) {

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YiProgressTheme {
        //
        Column {
            InterViewListItem(InterviewState("1", "腾讯", "android"))

            IndeterminateLinearIndicator()
            BottomSheet()
        }

    }
}