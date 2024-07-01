package com.walkS.yiprogress.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.walkS.yiprogress.MainIntent
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.entry.Profile


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


