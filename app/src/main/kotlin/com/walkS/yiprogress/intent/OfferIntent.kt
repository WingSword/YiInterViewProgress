package com.walkS.yiprogress.intent

import com.walkS.yiprogress.state.FormState

/**
 * Project YiProgress
 * Created by Wing on 2024/7/24 17:30.
 * Description:
 *
 **/
sealed class OfferIntent {
    data object SubmitOfferForm : OfferIntent()
    data object fetchOfferList : OfferIntent()
}