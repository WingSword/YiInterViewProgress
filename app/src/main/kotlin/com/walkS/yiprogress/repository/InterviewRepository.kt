package com.walkS.yiprogress.repository

import com.walkS.yiprogress.db.InterViewDao
import com.walkS.yiprogress.state.InterviewState
import kotlinx.coroutines.flow.Flow

class InterviewRepository(private val interviewDao: InterViewDao) {

    // 获取所有面试状态的流
    val interviews: Flow<List<InterviewState>> = interviewDao.loadAllInterviews()

    // 插入新的面试状态
    suspend fun upsertInterview(interview: InterviewState) {
        interviewDao.upsertInterview(interview)
    }


    // 删除面试状态
    suspend fun deleteInterview(interview: InterviewState) {
        interviewDao.deleteInterview(interview)
    }
}
