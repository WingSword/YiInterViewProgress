package com.walkS.yiprogress.state

data class InterviewState(
    val itemId: String,
    val title: String,
    val desc: String? = "",
    val progress: Int = 0,
    val email: String? = "",
)

data class InterViewStateList(
    val loading: Boolean = false,
    val list: List<InterviewState> = emptyList<InterviewState>()
)