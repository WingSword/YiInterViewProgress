package com.walkS.yiprogress.intent

sealed class MainIntent {
      object FetchData : MainIntent()
      object FetchDataList : MainIntent()
      object IsLoading : MainIntent()
}

