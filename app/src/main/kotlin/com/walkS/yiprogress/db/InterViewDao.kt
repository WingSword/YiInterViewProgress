package com.walkS.yiprogress.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.walkS.yiprogress.state.InterviewState
import kotlinx.coroutines.flow.Flow

@Dao
interface InterViewDao {

    // 插入单个InterviewState，返回rowId
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertInterview(data: InterviewState): Long

    // 批量插入InterviewState，返回受影响的行数
    // 注意：大量数据插入可能影响性能，应适当分批处理
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertInterviews(data: List<InterviewState>): List<Long>

    // 删除InterviewState，返回受影响的行数
    @Delete
    fun deleteInterview(data: InterviewState): Int

    // 加载所有InterviewState
    // 考虑到性能和内存使用，实际应用中可能需要实现分页
    @Query("SELECT * FROM interviews")
    fun loadAllInterviews(): Flow<List<InterviewState>>

    // 根据ID查找InterviewState
    @Query("SELECT * FROM interviews WHERE itemId = :id")
    fun findInterviewById(id: Int): InterviewState?

    // 根据IDs加载InterviewState
    @Query("SELECT * FROM interviews WHERE itemId IN (:ids)")
    fun loadInterviewsByIds(ids: IntArray): List<InterviewState>

    // 根据公司名和部门查找InterviewState
    // LIMIT 1优化性能，确保快速响应
    @Query("SELECT * FROM interviews WHERE companyName LIKE :companyName AND department LIKE :department LIMIT 1")
    fun findInterviewByCompanyAndDepartment(companyName: String, department: String): InterviewState?

    // 根据公司名和部门加载所有InterviewState
    // 实际应用中可能需要实现分页来提高性能
    @Query("SELECT * FROM interviews WHERE companyName LIKE :companyName AND department LIKE :department")
    fun loadInterviewsByCompanyAndDepartment(companyName: String, department: String): List<InterviewState>

    // 事务：批量操作
// 注意：事务中的操作应确保异常安全，适当处理可能的回滚情况
@Transaction
fun batchOperations(
    insertOrUpdate: List<InterviewState>,
    delete: List<InterviewState>
) {
    delete.forEach { deleteInterview(it) }
    insertOrUpdate.forEach { upsertInterview(it) }
}

}
