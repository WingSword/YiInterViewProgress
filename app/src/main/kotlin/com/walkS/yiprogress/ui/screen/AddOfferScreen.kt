package com.walkS.yiprogress.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.intent.OfferIntent
import com.walkS.yiprogress.state.FormState
import com.walkS.yiprogress.ui.theme.ChineseColor
import com.walkS.yiprogress.ui.widget.Form
import com.walkS.yiprogress.utils.Field
import com.walkS.yiprogress.utils.Required

/**
 * Project YiProgress
 * Created by Wing on 2024/7/26 14:30.
 * Description:
 *
 **/
@Composable
fun OfferPageScreen(

    navHostController: NavHostController,
    viewModel: MainViewModel
) {

}

@Composable
fun AddOfferDialog(
    onDismissRequest: () -> Unit,
    vm: MainViewModel
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.medium)
                .background(
                    color = ChineseColor.Su,
                )
        ) {
            AddOfferView(vm)
        }
    }
}

@Composable
fun AddOfferView(viewModel: MainViewModel) {
    val state by remember { mutableStateOf(FormState()) }
    Surface(modifier = Modifier.size(300.dp, 500.dp)) {
        Form(
            state = state,
            fields = listOf(
                Field(name = "companyName", label = "公司", validators = listOf(Required())),
                Field(name = "department", label = "部门", validators = listOf(Required())),
                Field(name = "job", label = "岗位", validators = listOf(Required())),
                Field(name = "salary", label = "薪资", validators = listOf(Required())),
                Field(
                    name = "yearEndBonusMonths",
                    label = "年终奖",
                    validators = listOf(Required())
                ),
                Field(name = "allowances", label = "补贴", validators = listOf(Required())),
                Field(
                    name = "housingFundBase",
                    label = "公积金基数",
                    validators = listOf(Required())
                ),
            )
        )
    }
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            if (state.validate()) {
                viewModel.handleOfferIntent(OfferIntent.SubmitOfferForm(state))
            }
        }) {
        Text("完成")
    }
}