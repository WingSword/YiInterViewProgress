package com.walkS.yiprogress.entry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alibaba.fastjson2.JSON
import com.blankj.utilcode.util.JsonUtils
import com.walkS.yiprogress.enum.InterviewStatus
import com.walkS.yiprogress.state.InterviewInfoState
import com.walkS.yiprogress.state.InterviewState
import kotlinx.serialization.json.Json

@Entity(tableName = "interviews")
data class InterviewEntry(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemId")
    val itemId: Long,
    @ColumnInfo(name = "companyName")
    val companyName: String,
    val department: String? = "",
    val job: String? = "",
    val interviewStatus: Int = InterviewStatus.UNKNOWN.ordinal,
    val progress: Int = 0,//当前进行阶段
    val progressNum: Int = 3,//总共阶段
    val interviewDetailedInfo: String,
    val createTime: String? = "",
    val info: String? = "",
    val interviewTime: String = "",
    val city: String? = "",
    val salary: Double? = 0.0,
    val salaryUnit: String = "K"
)

fun InterviewEntry.toState(): InterviewState {
    return InterviewState(
        itemId = itemId,
        companyName = companyName,
        department = department,
        job = job,
        interviewStatus = interviewStatus,
        progress = progress,
        progressNum = progressNum,
        interviewDetailedInfo = JSON.parseArray(
            interviewDetailedInfo,
            InterviewInfoState::class.java
        ),
        createTime = createTime,
        info = info,
        interviewTime = interviewTime,
        city = city,
        salary = salary,
        salaryUnit = salaryUnit
    )
}
