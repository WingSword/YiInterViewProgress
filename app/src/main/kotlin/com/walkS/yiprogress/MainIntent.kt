package com.walkS.yiprogress

sealed class MainIntent {
      object FetchData : MainIntent()
      object FetchDataList : MainIntent()
      object IsLoading : MainIntent()
}

