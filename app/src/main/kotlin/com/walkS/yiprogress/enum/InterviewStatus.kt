package com.walkS.yiprogress.enum

enum class InterviewStatus(val desc: String = "待确定") {
    UNKNOWN("待确定"),
    NOT_STARTED("待面试"),
    IN_PROGRESS("已面试"),
    COMPLETED("已通过"),
    FAILED("未通过")
}
