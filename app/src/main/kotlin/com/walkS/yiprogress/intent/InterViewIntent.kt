package com.walkS.yiprogress.intent

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

}