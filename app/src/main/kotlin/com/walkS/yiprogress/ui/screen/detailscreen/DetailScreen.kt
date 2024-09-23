package com.walkS.yiprogress.ui.screen.detailscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.entry.Constants
import com.walkS.yiprogress.enum.PageOperation
import com.walkS.yiprogress.intent.InterViewIntent
import com.walkS.yiprogress.ui.widget.AddInterView


@Composable
fun DetailScreen(viewModel: MainViewModel, navHostController: NavHostController) {

    val pageSate = viewModel.currentPageOperation.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.currentPageOperation.collect{
            when (it) {
                Constants.PAGE_OPERATION_COMPLETED -> navHostController.popBackStack()
                Constants.PAGE_OPERATION_REJECTED->viewModel.homeSnackBarHostState.value.showSnackbar("Invalid Data")
            }
        }
    }

    val interviewState = viewModel.interviewEditViewState.collectAsState()
    AddInterView(
        interviewState.value,
        pageSate.value
    ) { key, value ->
        viewModel.handleInterViewIntent(InterViewIntent.InterviewDataChanged(key, value ?: 0))
    }
}