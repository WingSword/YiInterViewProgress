package com.walkS.yiprogress


import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.walkS.yiprogress.db.AppDatabase
import com.walkS.yiprogress.intent.BottomSheetIntent
import com.walkS.yiprogress.intent.MainIntent
import com.walkS.yiprogress.state.InterViewStateList
import com.walkS.yiprogress.state.SnackBarHostState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    // 状态管理
    private val _interviewListState = MutableStateFlow(InterViewStateList())
    val listState: StateFlow<InterViewStateList> = _interviewListState

    private val _isShowBottomSheet = MutableStateFlow(false)
    val isShowBottomSheet: StateFlow<Boolean> = _isShowBottomSheet

    private val _homeSnackBarState = MutableStateFlow( SnackbarHostState())
    val homeSnackBarHostState: StateFlow< SnackbarHostState> = _homeSnackBarState

    // 处理意图
    fun handleInterViewList(intent: MainIntent) {
        when (intent) {
            MainIntent.FetchData -> fetchData()
            MainIntent.FetchDataList -> {

                fetchData()
                saveDataToLocal()
            }

            MainIntent.IsLoading -> {
                // 处理加载状态
            }
        }
    }

    fun handleBottomIntent(intent: BottomSheetIntent) {
        when (intent) {
            BottomSheetIntent.CloseSheet -> _isShowBottomSheet.value = false
            BottomSheetIntent.IsLoading -> {
                // 处理加载状态
            }

            BottomSheetIntent.OpenSheet -> _isShowBottomSheet.value = true
        }
    }

    private fun fetchData() {
        _interviewListState.value = _interviewListState.value.copy(isFreshing = true)

        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            try {
                val db = AppDatabase.getInstance(MainApplication.appContext)

                if (db != null) {
                    val list = db.interViewDao()?.loadAllInterviews()
                    list?.let {
                        _interviewListState.value = _interviewListState.value.copy(
                            isFreshing = false,
                            list = list
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    _homeSnackBarState.value.showSnackbar("刷新失败：${e.message}")
                }
                // 异常处理逻辑，例如记录日志或更新UI状态
                _interviewListState.value = _interviewListState.value.copy(isFreshing = false)
            }
        }
    }


    private fun saveDataToLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val db = AppDatabase.getInstance(MainApplication.appContext)
                db?.runInTransaction {
                    // 建议：考虑实现批量插入以提高性能
                    for (data in listState.value.list) {
                        db.interViewDao()?.insertInterview(data)
                    }
                }
            } catch (e: Exception) {
                // 异常处理逻辑
            }
        }
    }
}

