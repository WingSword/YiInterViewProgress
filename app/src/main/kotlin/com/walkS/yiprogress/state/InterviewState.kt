package com.walkS.yiprogress.state


data class InterviewState(
    val itemId: String,
    val title: String,
    val department:String?="",
    val job: String? = "",
    val interviewStatus: String? = "",
    val progress: Int = 0,//当前进行阶段
    val progressNum: Int = 3,//总共阶段
    val time: String? = "",
    val info: String? = "",
)

data class InterViewStateList(
    val isFreshing: Boolean = false,
    val list: List<InterviewState> = emptyList<InterviewState>()
)