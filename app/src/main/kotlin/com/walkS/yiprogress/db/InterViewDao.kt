package com.walkS.yiprogress.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.walkS.yiprogress.state.InterviewState

@Dao
interface InterViewDao {

    // 插入单个InterviewState，返回rowId
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInterview(data: InterviewState): Long

    // 批量插入InterviewState，返回受影响的行数
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInterviews(vararg data: InterviewState): List<Long>

    // 删除InterviewState，返回受影响的行数
    @Delete
    fun deleteInterview(data: InterviewState): Int

    // 更新InterviewState，返回受影响的行数
    @Update
    fun updateInterview(data: InterviewState): Int

    // 加载所有InterviewState
    @Query("SELECT * FROM interviews")
    fun loadAllInterviews(): List<InterviewState>

    // 根据ID查找InterviewState
    @Query("SELECT * FROM interviews WHERE itemId = :id")
    fun findInterviewById(id: Int): InterviewState?

    // 根据IDs加载InterviewState
    @Query("SELECT * FROM interviews WHERE itemId IN (:ids)")
    fun loadInterviewsByIds(ids: IntArray): List<InterviewState>

    // 根据公司名和部门查找InterviewState
    @Query("SELECT * FROM interviews WHERE companyName LIKE :companyName AND department LIKE :department LIMIT 1")
    fun findInterviewByCompanyAndDepartment(companyName: String, department: String): InterviewState?

    // 根据公司名和部门加载所有InterviewState
    @Query("SELECT * FROM interviews WHERE companyName LIKE :companyName AND department LIKE :department")
    fun loadInterviewsByCompanyAndDepartment(companyName: String, department: String): List<InterviewState>

    // 事务：批量操作
    @Transaction
    fun batchOperations(
        insert: List<InterviewState>,
        delete: List<InterviewState>,
        update: List<InterviewState>
    ) {
        insert.forEach { insertInterview(it) }
        delete.forEach { deleteInterview(it) }
        update.forEach { updateInterview(it) }
    }
}
