package com.walkS.yiprogress.state

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "interviews")
data class InterviewState(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemId")
    val itemId: Long,
    @ColumnInfo(name = "companyName")
    val companyName: String,
    val department: String? = "",
    val job: String? = "",
    val interviewStatus: String? = "",
    val progress: Int = 0,//当前进行阶段
    val progressNum: Int = 3,//总共阶段
    val time: String? = "",
    val info: String? = "",
    val interviewTime:String="",
    val city:String?="",
    val salary:Double?=0.0,
)
data class InterViewStateList(
    val isFreshing: Boolean = false,
    val list: List<InterviewState> = emptyList<InterviewState>()
)