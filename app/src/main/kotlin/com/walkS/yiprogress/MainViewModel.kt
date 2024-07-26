package com.walkS.yiprogress


import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walkS.yiprogress.db.AppDatabase
import com.walkS.yiprogress.intent.InterViewIntent
import com.walkS.yiprogress.intent.MainIntent
import com.walkS.yiprogress.intent.OfferIntent
import com.walkS.yiprogress.repository.InterviewRepository
import com.walkS.yiprogress.repository.OfferRepository
import com.walkS.yiprogress.state.InterViewStateList
import com.walkS.yiprogress.state.OfferState
import com.walkS.yiprogress.state.OfferStateList
import com.walkS.yiprogress.utils.RandomUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    // 状态管理
    private val _interviewListState = MutableStateFlow(InterViewStateList())
    val listState: StateFlow<InterViewStateList> = _interviewListState

    private val _offerListState = MutableStateFlow(OfferStateList())
    val offerListState: StateFlow<OfferStateList> = _offerListState

    private val _isShowBottomSheet = MutableStateFlow(false)
    val isShowBottomSheet: StateFlow<Boolean> = _isShowBottomSheet

    private val _isShowOfferDialog = MutableStateFlow(false)
    val isShowOfferDialog: StateFlow<Boolean> = _isShowOfferDialog

    private val _homeSnackBarState = MutableStateFlow(SnackbarHostState())
    val homeSnackBarHostState: StateFlow<SnackbarHostState> = _homeSnackBarState
    private val interviewRepository: InterviewRepository by lazy {
        InterviewRepository(
            AppDatabase.getInstance(
                MainApplication.appContext
            )!!.interViewDao()
        )
    }
    private val offerRepository: OfferRepository by lazy {
        OfferRepository(
            AppDatabase.getInstance(
                MainApplication.appContext
            )!!.offerDao()
        )
    }

    // 处理意图
    fun handleMainIntent(intent: MainIntent) {
        when (intent) {

            MainIntent.CloseDialog -> _isShowOfferDialog.value = false
            MainIntent.CloseSheet -> _isShowBottomSheet.value = false

            is MainIntent.OpenDialog -> _isShowOfferDialog.value = true
            MainIntent.OpenSheet -> _isShowBottomSheet.value = true
        }
    }

    fun handleInterViewIntent(intent: InterViewIntent) {
        when (intent) {
            InterViewIntent.FetchData -> fetchData()
            InterViewIntent.FetchDataList -> {
                fetchData()
                saveDataToLocal()
            }

            InterViewIntent.IsLoading -> {
                //no need
            }
        }
    }

    //offer
    fun handleOfferIntent(intent: OfferIntent) {
        when (intent) {
            is OfferIntent.SubmitOfferForm -> {
                // 数据验证
                val formStateData = intent.formState.getData()
                val companyName = formStateData["companyName"] ?: ""
                val department = formStateData["department"] ?: ""

                if (companyName.isEmpty() || department.isEmpty()) {
                    // 处理数据不完整的情况
                    return
                }

                val offerState = OfferState(
                    offerId = RandomUtils.optOfferRandomId(),
                    companyName = formStateData["companyName"] ?: "",
                    department = formStateData["department"] ?: "",
                    salary = formStateData["salary"]?.toDouble() ?: 0.0,

                    )

                viewModelScope.launch {
                    try {
                        val result =
                            async(Dispatchers.IO) { offerRepository.upsertOffer(offerState) }.await()
                        if (result == offerState.offerId) {
                            _isShowBottomSheet.value = false
                            val list = _offerListState.value.list.toMutableList()
                            list.add(offerState)
                            _offerListState.value = _offerListState.value.copy(
                                isRefreshing = false,
                                list = list
                            )
                        }
                    } catch (e: Exception) {
                        // 异常处理，例如记录日志或向用户显示错误消息
                        Log.e("OfferViewModel", "Failed to upsert interview", e)
                        // 可以考虑在这里向UI反馈错误信息
                    }
                }
            }

            OfferIntent.fetchOfferList -> {
                _offerListState.value = _offerListState.value.copy(isRefreshing = true)
                viewModelScope.launch {
                    try {
                        offerRepository.offers.collectLatest { offerList ->
                            withContext(Dispatchers.Main) {
                                _offerListState.value = _offerListState.value.copy(
                                    isRefreshing = false,
                                    list = offerList
                                )
                            }
                        }
                    } catch (e: Exception) {
                        // 异常处理
                        Log.e("OfferViewModel", "Failed to fetch offer list", e)
                        // 考虑向UI反馈错误信息
                    } finally {
                        // 确保在finally块中取消collect操作，避免资源泄露
                        coroutineContext.cancelChildren()
                    }
                }
            }
        }
    }

    private fun fetchData() {
        _interviewListState.value = _interviewListState.value.copy(isFreshing = true)

        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            interviewRepository.interviews.collect {
                _interviewListState.value = _interviewListState.value.copy(
                    isFreshing = false,
                    list = it
                )
            }
        }
    }

    private fun saveDataToLocal() {
        viewModelScope.launch {
            for (data in listState.value.list) {
                interviewRepository.upsertInterview(data)
            }
        }
    }
}

