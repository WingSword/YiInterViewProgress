package com.walkS.yiprogress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.blankj.utilcode.util.LogUtils
import com.walkS.yiprogress.state.InterViewStateList
import com.walkS.yiprogress.state.InterviewState
import com.walkS.yiprogress.utils.DateUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // 状态管理
    private val _interviewListState = MutableStateFlow(InterViewStateList())
    val listState: StateFlow<InterViewStateList> = _interviewListState

    var navi: NavController? = null
    private var time = 0

    // 处理意图
    fun handleIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.FetchData -> {

            }

            MainIntent.FetchDataList -> {
                _interviewListState.value = _interviewListState.value.copy(isFreshing = true)
                viewModelScope.launch {
                    time++
                    delay(2000)
                    _interviewListState.value = _interviewListState.value.copy(
                        isFreshing = false,
                        list = listOf(
                            InterviewState(
                                "001",
                                "腾讯-音乐",
                                job = "android开发",
                                time = "",
                                progressNum = 4,
                                progress = 1
                            )
                        )
                    )
                    LogUtils.d("添加数据---${_interviewListState.value.list}")
                    LogUtils.d("state数据(listState)---${listState.value.list}")
                }
            }

            MainIntent.IsLoading -> {

            }
        }
    }
}

