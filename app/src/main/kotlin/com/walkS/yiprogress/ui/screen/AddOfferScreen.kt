package com.walkS.yiprogress.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.intent.OfferIntent
import com.walkS.yiprogress.state.FormState
import com.walkS.yiprogress.state.OfferState
import com.walkS.yiprogress.ui.widget.Form
import com.walkS.yiprogress.utils.Field
import com.walkS.yiprogress.utils.RandomUtils
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

) {

    Dialog(onDismissRequest = { onDismissRequest() },) {
        AddOfferView()
    }
}

@Composable
fun AddOfferView() {
    val vm: MainViewModel = viewModel()
    val offerState = remember { OfferState(RandomUtils.optOfferRandomId()) }
    val state by remember { mutableStateOf(FormState()) }

    Column {
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
        Button(onClick = {
            if (state.validate()) {
                vm?.handleOfferIntent(OfferIntent.SubmitOfferForm(state))
            }
        }) {
            Text("完成")
        }
    }
}