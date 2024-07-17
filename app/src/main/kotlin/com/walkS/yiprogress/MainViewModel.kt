package com.walkS.yiprogress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.walkS.yiprogress.db.AppDatabase
import com.walkS.yiprogress.intent.BottomSheetIntent
import com.walkS.yiprogress.state.InterViewStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // 状态管理
    private val _interviewListState = MutableStateFlow(InterViewStateList())
    val listState: StateFlow<InterViewStateList> = _interviewListState

    private val _isShowBottomSheet = MutableStateFlow(false)
    val isShowBottomSheet: StateFlow<Boolean> = _isShowBottomSheet

    var navi: NavController? = null


    // 处理意图
    fun handleInterViewList(intent: MainIntent) {
        when (intent) {
            MainIntent.FetchData -> {

            }

            MainIntent.FetchDataList -> {
                getDataFromLocal()
                saveDataToLocal()
            }

            MainIntent.IsLoading -> {
            }
        }
    }

    fun handleBottomIntent(intent: BottomSheetIntent) {
        when (intent) {
            BottomSheetIntent.CloseSheet -> _isShowBottomSheet.value = false
            BottomSheetIntent.IsLoading -> {}
            BottomSheetIntent.OpenSheet -> _isShowBottomSheet.value = true
        }
    }


    private fun getDataFromLocal() {
        viewModelScope.launch {
            val db = AppDatabase.getInstance(MainApplication.appContext)
            val list = db?.interViewDao()?.loadAllInterviews()
            db?.close()
            list?.let {
                _interviewListState.value = _interviewListState.value.copy(
                    isFreshing = false,
                    list = list
                )
            }
        }
    }

    private fun saveDataToLocal() {
        viewModelScope.launch {
            val db = AppDatabase.getInstance(MainApplication.appContext)
            db?.runInTransaction {
                for (data in listState.value.list) {
                    db.interViewDao()?.insertInterview(data)
                }
            }
            db?.close()
        }
    }
}

