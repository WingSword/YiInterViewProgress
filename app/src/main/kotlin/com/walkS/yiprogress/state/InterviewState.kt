package com.walkS.yiprogress.state


data class InterviewState(
    val itemId: Long,
    val companyName: String,
    val department: String? = "",
    val job: String? = "",
    val interviewStatus: String? = "",
    val progress: Int = 0,//当前进行阶段
    val progressNum: Int = 3,//总共阶段
    val interInfo: List<InterviewInfoState> = emptyList(),
    val createTime: String? = "",
    val info: String? = "",
    val interviewTime: String = "",
    val city: String? = "",
    val salary: Double? = 0.0,
    val salaryUnit: String = "K",
)

data class InterviewInfoState(
    val interviewNo: Int = 0,
    val interviewName: String? = "",
    val interviewTime: String? = "",
    val interviewAddress: String? = "",
    val interviewStatus: Int? = 0,
    val interviewRemark: String? = "",
)

data class InterViewStateList(
    val isFreshing: Boolean = false,
    val list: List<InterviewState> = emptyList<InterviewState>()
)

data class InterViewStateEdit(
    val isCommited: Boolean = false,
    val checked: Boolean = false,
    val data: InterviewState? = null
)