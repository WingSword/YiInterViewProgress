package com.walkS.yiprogress.repository

import com.walkS.yiprogress.db.InterViewDao
import com.walkS.yiprogress.entry.toState
import com.walkS.yiprogress.state.InterviewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InterviewRepository(private val interviewDao: InterViewDao) {

    // 获取所有面试状态的流
    val interviews: Flow<List<InterviewState>> =
        interviewDao.loadAllInterviews().map { list -> list.map { it.toState() } }

    // 插入新的面试状态
    suspend fun upsertInterview(interview: InterviewState): Long {
        return interviewDao.upsertInterview(interview.toEntry())
    }


    // 删除面试状态
    suspend fun deleteInterview(interview: InterviewState) {
        interviewDao.deleteInterview(interview.toEntry())
    }
}
