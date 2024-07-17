package com.walkS.yiprogress.intent

/**
 * Project YiProgress
 * Created by Wing on 2024/7/17 14:10.
 * Description:
 *
 **/
sealed class BottomSheetIntent {
    data object OpenSheet : BottomSheetIntent()
    data object CloseSheet : BottomSheetIntent()
    data object IsLoading : BottomSheetIntent()
}