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


    private val _isShowViewDialog = MutableStateFlow(DIALOG_TYPE_DISMISS)
    val isShowViewDialog: StateFlow<Int> = _isShowViewDialog

    private val _homeSnackBarState = MutableStateFlow(SnackbarHostState())
    val homeSnackBarHostState: StateFlow<SnackbarHostState> = _homeSnackBarState

    companion object {
        const val DIALOG_TYPE_DISMISS = 0
        const val DIALOG_TYPE_SHOW_ADD_INTERVIEW = 1
        const val DIALOG_TYPE_SHOW_ADD_OFFER = 2
    }

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

            MainIntent.CloseDialog -> {
                _isShowViewDialog.value = DIALOG_TYPE_DISMISS
            }

            MainIntent.CloseSheet -> _isShowBottomSheet.value = false

            is MainIntent.OpenDialog -> {
                _isShowViewDialog.value = intent.type
            }

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

            is InterViewIntent.NewInterView -> {

            }
        }
    }

    //offer
    fun handleOfferIntent(intent: OfferIntent) {
        when (intent) {
            is OfferIntent.SubmitOfferForm -> {
                // 数据验证
                val formStateData = intent.formState.getData()
                val offerState = OfferState(
                    offerId = RandomUtils.optOfferRandomId(),
                    companyName = formStateData["companyName"].toString(),
                    department = formStateData["department"].toString(),
                    job = formStateData["job"].toString(),
                    salary = formStateData.get("salary") as? Double ?: 0.0,
                    yearEndBonusMonths = formStateData.get("yearEndBonusMonths")
                            as? Double ?: 0.0,
                    allowances = formStateData.get("allowances") as? Double ?: 0.0,
                    annualPackagePreTax = formStateData.get("annualPackagePreTax")
                            as? Double ?: 0.0,
                    socialInsuranceBase = formStateData.get("socialInsuranceBase")
                            as? Double ?: 0.0,
                    housingFundBase = formStateData.get("housingFundBase")
                            as? Double ?: 0.0,
                    socialInsuranceRate = formStateData.get("socialInsuranceRate")
                            as? Float ?: 0f,
                    supplementaryHousingFund = formStateData.get("supplementaryHousingFund")
                            as? Double ?: 0.0,
                    housingFund = formStateData.get("housingFund") as? Double ?: 0.0,
                    monthlyNetSalary = formStateData.get("monthlyNetSalary") as? Double ?: 0.0,
                    monthlyIncome = formStateData.get("monthlyIncome") as? Double ?: 0.0,
                    workingHours = formStateData["workingHours"].toString(),
                    overtimeIntensity = formStateData["overtimeIntensity"].toString(),
                    businessTripFrequency = formStateData["businessTripFrequency"].toString(),
                    professionalMatch = formStateData.get("professionalMatch") as? Boolean ?: false,
                    careerDevelopmentHelp = formStateData.get("careerDevelopmentHelp")
                            as Boolean,
                    promotionPotential = formStateData.get("promotionPotential") as? Boolean
                        ?: false,
                    companySizeAndInfluence = formStateData["companySizeAndInfluence"].toString(),
                    futureProspects = formStateData["futureProspects"].toString(),
                    otherDetails = formStateData["otherDetails"].toString(),
                    additionalInformation = formStateData["additionalInformation"].toString()
                )

                viewModelScope.launch {
                    try {
                        val result =
                            async(Dispatchers.IO) { offerRepository.upsertOffer(offerState) }.await()
                        if (result == offerState.offerId) {
                            _isShowViewDialog.value = DIALOG_TYPE_DISMISS
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

