package com.walkS.yiprogress.intent

import com.walkS.yiprogress.state.FormState
import com.walkS.yiprogress.state.InterviewState

/**
 * Project YiProgress
 * Created by Wing on 2024/7/26 16:03.
 * Description:
 *
 **/
sealed class InterViewIntent {
    data object FetchData : InterViewIntent()
    data object FetchDataList : InterViewIntent()
    data object IsLoading : InterViewIntent()
    data class NewInterView(val formState: InterviewState) : InterViewIntent()
    data class InterviewDataChanged(val key:String,val data:String):InterViewIntent()

}