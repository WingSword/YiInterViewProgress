package com.walkS.yiprogress.intent

sealed class MainIntent {
    data object OpenSheet : MainIntent()
    data object CloseSheet : MainIntent()


    data class OpenDialog(val type: Int ) : MainIntent()
    data object CloseDialog : MainIntent()
}

