package com.walkS.yiprogress.ui.widget

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.intent.BottomSheetIntent
import com.walkS.yiprogress.ui.screen.homescreen.AddInterView
import com.walkS.yiprogress.ui.screen.homescreen.isHomeScreenPage

@Preview
@Composable
fun NavigationBottomLayout(navi: NavController, currentRoute: String?) {
    if (isHomeScreenPage(currentRoute)) {
        NavigationBar(

        ) {
            Profile.entries.filter { isHomeScreenPage(it.route) }.forEach { label ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(label.iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    },
                    selected = currentRoute == label.route,
                    onClick = {
                        navi.navigate(label.route) {
                            popUpTo(navi.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    //selectedContentColor = ChineseColor.KuHuang
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(vm: MainViewModel) {
    val sheetState = rememberModalBottomSheetState(
        false
    )
    val isShow = vm.isShowBottomSheet.collectAsState()
    if (isShow.value) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { vm.handleBottomIntent(BottomSheetIntent.CloseSheet) }
        ) {
            AddInterView()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTopBar(navi: NavController, currentRoute: String?) {

    CenterAlignedTopAppBar(
        title = { Text(Profile.fromRoute(currentRoute)?.title ?: "") },
        navigationIcon = {
            IconButton(onClick = {
                if (isHomeScreenPage(currentRoute)) {
                    navi.navigate(Profile.DETAIL_INTERVIEW.route)
                } else {
                    navi.popBackStack()
                }
            }) {
                Icon(
                    imageVector = if (isHomeScreenPage(currentRoute)) Icons.AutoMirrored.Filled.List else Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
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
fun IndeterminateCircularIndicator(size: Dp = 64.dp) {
    CircularProgressIndicator(
        modifier = Modifier.width(size),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}


