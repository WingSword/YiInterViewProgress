package com.walkS.yiprogress.ui.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.updateTransition
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.MainViewModel.Companion.DIALOG_TYPE_SHOW_ADD_INTERVIEW
import com.walkS.yiprogress.MainViewModel.Companion.DIALOG_TYPE_SHOW_ADD_OFFER
import com.walkS.yiprogress.entry.Profile
import com.walkS.yiprogress.intent.InterViewIntent
import com.walkS.yiprogress.intent.MainIntent
import com.walkS.yiprogress.intent.OfferIntent
import com.walkS.yiprogress.ui.screen.homescreen.isHomeScreenPage
import com.walkS.yiprogress.ui.theme.ChineseColor

@Preview
@Composable
fun NavigationBottomLayout(navi: NavController, currentRoute: String?) {
    if (isHomeScreenPage(currentRoute)) {
        NavigationBar(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .height(60.dp),

            ) {
            Profile.entries.filter { isHomeScreenPage(it.route) }.forEach { label ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(label.iconRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.CenterVertically)

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
                    alwaysShowLabel = false,
                    //selectedContentColor = ChineseColor.KuHuang
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(navController: NavHostController, vm: MainViewModel) {
    val sheetState = rememberModalBottomSheetState(
        false
    )
    val isShow = vm.isShowBottomSheet.collectAsState()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    if (isShow.value) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { vm.handleMainIntent(MainIntent.CloseSheet) }
        ) {


        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTopBar(navi: NavController, currentRoute: String?, viewModel: MainViewModel) {
    CenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            AnimatedVisibility(!isHomeScreenPage(currentRoute)) {
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = CircleShape
                        ),
                    onClick = {
                        navi.popBackStack()
                    },
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        disabledContentColor = Color.Red,
                        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back button"
                    )
                }
            }
        },
        actions = {
            TopActionButton(isHomeScreenPage(currentRoute)) {
                val screen = navi.currentBackStackEntry?.destination

                when (screen?.route) {
                    Profile.HOME_OFFER_LIST_PAGE.route -> {
                        navi.navigate(Profile.DETAIL_OFFER.route)
//                                viewModel.handleMainIntent(
//                                    MainIntent.OpenDialog(DIALOG_TYPE_SHOW_ADD_OFFER)
//                                )
                    }

                    Profile.HOME_INTERVIEW_LIST_PAGE.route -> {
                        navi.navigate(Profile.DETAIL_INTERVIEW.route)
//                            viewModel.handleMainIntent(
//                                MainIntent.OpenDialog(DIALOG_TYPE_SHOW_ADD_INTERVIEW)
//                            )
                    }

                    Profile.DETAIL_INTERVIEW.route -> {
                        viewModel.handleInterViewIntent(InterViewIntent.NewInterView)
                    }

                    Profile.DETAIL_OFFER.route -> {
                        if(viewModel.offerDetailFormState.validate()){
                            viewModel.handleOfferIntent(OfferIntent.SubmitOfferForm)
                            navi.popBackStack()
                        }
                    }
                }
            }


        },
        modifier = Modifier.padding(24.dp)
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopActionButton(isExtend: Boolean, onActionButtonClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isExtend) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
        label = "background color"
    )
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .combinedClickable(
                enabled = isExtend,
                onClick = { onActionButtonClick.invoke() }
            )
            .background(color = backgroundColor, shape = CircleShape)
            .padding(end = 4.dp, start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,

        ) {
        AnimatedVisibility(isExtend) {
            Text(
                modifier = Modifier.padding(end = 4.dp),
                text = "Add",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        IconButton(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = if (!isExtend) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondary,
                    shape = CircleShape
                ),
            onClick = {
                onActionButtonClick.invoke()
            },
            colors = IconButtonColors(
                containerColor = if (!isExtend) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                contentColor = if (!isExtend) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer,

                disabledContentColor = Color.Red,
                disabledContainerColor = Color.Black,
            ),
        ) {
            Icon(
                imageVector = if (!isExtend) Icons.Filled.Done else Icons.Filled.Add,
                contentDescription = "done button",
            )
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


@Composable
fun TotalDialog(viewModel: MainViewModel) {

    val isShowAddInterViewDialog = viewModel.isShowViewDialog.collectAsState()
    when (isShowAddInterViewDialog.value) {
        DIALOG_TYPE_SHOW_ADD_OFFER -> {
            BaseDialog(onDismissRequest = { viewModel.handleMainIntent(MainIntent.CloseDialog) }) {
                AddOfferView(
                    viewModel
                )
            }
        }

        DIALOG_TYPE_SHOW_ADD_INTERVIEW -> {
            BaseDialog(onDismissRequest = { viewModel.handleMainIntent(MainIntent.CloseDialog) }) {

            }
        }
    }
}

@Composable
fun BaseDialog(
    onDismissRequest: () -> Unit,
    content: @Composable (onDismissRequest: () -> Unit) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.medium)
                .background(
                    color = ChineseColor.Su,
                )
        ) {
            content.invoke(onDismissRequest)
        }
    }
}



