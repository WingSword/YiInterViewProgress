package com.walkS.yiprogress.ui.screen.detailscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.intent.InterViewIntent
import com.walkS.yiprogress.state.FormState
import com.walkS.yiprogress.ui.widget.AddInterView


@Composable
fun DetailScreen(viewModel: MainViewModel) {

    val interviewTime = remember {
        mutableStateOf("")
    }
    val selectInterViewState = remember {
        mutableStateOf("待面试")
    }
    viewModel.handleInterViewIntent(
        InterViewIntent.InterviewDataChanged(
            "interviewStatus",
            selectInterViewState.value
        ))



    AddInterView(
        viewModel,
        formState =FormState(),
        selectInterViewState
    )
}