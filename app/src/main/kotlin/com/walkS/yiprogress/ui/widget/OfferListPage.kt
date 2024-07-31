package com.walkS.yiprogress.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.R
import com.walkS.yiprogress.intent.OfferIntent
import com.walkS.yiprogress.state.OfferState
import com.walkS.yiprogress.ui.theme.YiProgressTheme
import kotlinx.coroutines.launch

/**
 *   Created by Administrator on 2024/7/7.
 *   Description:
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OfferListPage(viewModel: MainViewModel) {
    val refreshScope = rememberCoroutineScope()
    val state = viewModel.offerListState.collectAsState().value

    //在这里做网络耗时操作
    fun refresh() = refreshScope.launch {
        viewModel.handleOfferIntent(OfferIntent.fetchOfferList)
    }

    val pullState = rememberPullRefreshState(state.isRefreshing, ::refresh)
    Box(Modifier.pullRefresh(pullState)) {
        if (state.isRefreshing) {
            IndeterminateLinearIndicator()
        } else {
            LazyVerticalGrid(
                GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.list.size) { pos ->
                    OfferItem(state.list[pos])
                }
                if (state.list.isEmpty()) {
                    item {
                        Spacer(modifier = Modifier.fillMaxSize())
                    }
                }
            }
            if (state.list.isEmpty()) {
                EmptyListWidget()
            }

        }
        PullRefreshIndicator(state.isRefreshing, pullState, Modifier.align(Alignment.TopCenter))
    }
}


@Composable
fun OfferItem(state: OfferState) {
    Card(modifier = Modifier.size(200.dp)) {
        Image(
            painter = painterResource(id = R.drawable.papertexture),
            contentDescription = "卡片背景",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
        ) {
            Text(text = state.companyName + state.department)
            Text(text = state.job)
            Text(text = "税前薪资:" + state.salary)
            Text(text = "年终奖:" + state.yearEndBonusMonths)
            Text(text = "补贴:" + state.allowances)
            Text(text = "公积金:" + state.housingFund.toInt())
        }
    }


}

@Composable
fun MinePage() {


    //

}

@Preview(showBackground = true, widthDp = 400, heightDp = 480)
@Composable
fun GreetingPreview() {
    YiProgressTheme {
        Row {
            OfferItem(
                OfferState(
                    1,
                    "长安汽车",
                    "技术部",
                    "ANDROID开发工程师",
                    "2023-07-07",
                    "北京",
                    10,
                    12000.0,
                    50000.0,
                    875.0,
                    workingHours = "8:00-17:00",
                    overtimeIntensity = "1",
                    businessTripFrequency = "true",
                    professionalMatch = true,
                    careerDevelopmentHelp = true,
                    promotionPotential = true,
                    companySizeAndInfluence = "1",
                    futureProspects = "1",
                    otherDetails = "1",
                    additionalInformation = "1",
                )
            )

            OfferItem(
                OfferState(
                    1,
                    "长安汽车",
                    "技术部",
                    "ANDROID开发工程师",
                    "2023-07-07",
                    "北京",
                    10,
                    12000.0,
                    50000.0,
                    875.0,
                    workingHours = "8:00-17:00",
                    overtimeIntensity = "1",
                    businessTripFrequency = "true",
                    professionalMatch = true,
                    careerDevelopmentHelp = true,
                    promotionPotential = true,
                    companySizeAndInfluence = "1",
                    futureProspects = "1",
                    otherDetails = "1",
                    additionalInformation = "1",
                )
            )
        }

    }
}