package com.walkS.yiprogress.state

import com.alibaba.fastjson2.JSON
import com.walkS.yiprogress.entry.InterviewEntry
import com.walkS.yiprogress.enum.InterviewStatus


data class InterviewState(
    val itemId: Long,
    val companyName: String,
    val department: String? = "",
    val job: String? = "",
    val interviewStatus: Int = InterviewStatus.UNKNOWN.ordinal,
    val progress: Int = 0,//当前进行阶段
    val progressNum: Int = 3,//总共阶段
    val interviewDetailedInfo: List<InterviewInfoState> = emptyList(),
    val createTime: String? = "",
    val info: String? = "",
    val interviewTime: String = "",
    val city: String? = "",
    val salary: Double? = 0.0,
    val salaryUnit: String = "K"
) {
    fun toEntry(): InterviewEntry {
        return InterviewEntry(
            itemId = itemId,
            companyName = companyName,
            department = department,
            job = job,
            interviewStatus = interviewStatus,
            progress = progress,
            progressNum = progressNum,
            interviewDetailedInfo = JSON.toJSONString(interviewDetailedInfo),
            createTime = createTime,
            info = info,
            interviewTime = interviewTime,
            city = city,
            salary = salary,
            salaryUnit = salaryUnit
        )
    }
}

data class InterviewInfoState(
    val interviewNo: Int = 0,
    val interviewName: String? = "",
    val interviewTime: String? = "",
    val interviewAddress: String? = "",
    val interviewStatus: Int? = 0,
    val interviewRemark: String? = "",
)

data class InterViewStateList(
    val isRefreshing: Boolean = false,
    val list: List<InterviewState> = emptyList<InterviewState>()
)
