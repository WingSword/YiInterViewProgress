package com.walkS.yiprogress.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.walkS.yiprogress.MainViewModel
import com.walkS.yiprogress.intent.OfferIntent
import com.walkS.yiprogress.state.FormState
import com.walkS.yiprogress.ui.theme.ChineseColor
import com.walkS.yiprogress.ui.field.TextInputField
import com.walkS.yiprogress.ui.field.Required
import com.walkS.yiprogress.ui.widget.Form

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
    val formList = listOf(
        TextInputField(name = "companyName", label = "公司", validators = listOf(Required)),
        TextInputField(name = "department", label = "部门", validators = listOf(Required)),
        TextInputField(name = "job", label = "岗位", validators = listOf(Required)),
        TextInputField(name = "job", label = "工作城市", validators = listOf(Required)),

        TextInputField(
            name = "salary",
            label = "薪资（元）",
            keyboardType = KeyboardType.Number,
            validators = listOf(Required)
        ),
        TextInputField(
            name = "yearEndBonusMonths",
            label = "年终奖（元）",
            keyboardType = KeyboardType.Number,
            validators = listOf()
        ),
        TextInputField(
            name = "allowances",
            label = "补贴（元/月）",
            keyboardType = KeyboardType.Number,
            validators = listOf()
        ),
        TextInputField(
            name = "housingFundBase",
            label = "公积金基数（元）",
            keyboardType = KeyboardType.Number,
            validators = listOf()
        ),
        TextInputField(
            name = "socialInsuranceRate",
            label = "公积金缴纳比例（%）",
            keyboardType = KeyboardType.Number,
            validators = listOf()
        ),
        TextInputField(
            "overtimeIntensity",
            label = "加班强度",
            validators = listOf()
        ),
        TextInputField(
            name = "futureProspects",
            label = "未来发展可能性",
            inputLines = 3,
            validators = listOf()
        ),
        TextInputField(
            name = "otherDetails",
            label = "其他",
            inputLines = 3,
            validators = listOf()
        ),

        )

    Surface(modifier = Modifier.size(300.dp, 500.dp)) {
        Form(state = state, fields = formList)
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

@Composable
fun AddOfferForm() {

}