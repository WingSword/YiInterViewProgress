package com.walkS.yiprogress.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.walkS.yiprogress.state.OfferState

/**
 * Project YiProgress
 * Created by Wing on 2024/7/24 14:45.
 * Description:用于管理OfferState实体的数据访问对象。
 */

@Dao
interface OfferDao {

    /**
     * 插入或替换多个OfferState实体。
     * @param data 要插入或替换的OfferState实体列表。
     * @return 与插入或替换的实体相对应的行ID列表。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOffers(data: List<OfferState>): List<Long>

    /**
     * 插入或替换单个OfferState实体。
     * @param data 要插入或替换的OfferState实体。
     * @return 插入或替换的实体的行ID。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun upsertOffer(data: OfferState): Long

    /**
     * 获取所有OfferState实体。
     * @return 所有OfferState实体的列表。
     */
    @Query("SELECT * FROM offers")
    fun getAllOffers(): List<OfferState>

    /**
     * 根据ID删除单个OfferState实体。
     * @param id 要删除的OfferState实体的ID。
     */
    @Query("DELETE FROM offers WHERE offerId = :id")
    suspend fun deleteOfferById(id: Long)

    /**
     * 根据ID列表删除多个OfferState实体。
     * @param ids 要删除的OfferState实体的ID列表。
     */
    @Query("DELETE FROM offers WHERE offerId IN (:ids)")
    suspend fun deleteOffersByIds(ids: List<Long>)

    /**
     * 更新单个OfferState实体。
     * @param data 要更新的OfferState实体。
     * @return 被更新的实体数量。
     */
    @Update
    suspend fun updateOffer(data: OfferState): Int

    /**
     * 计算OfferState实体的总数。
     * @return OfferState实体的总数。
     */
    @Query("SELECT COUNT(*) FROM offers")
    suspend fun countOffers(): Int

    /**
     * 根据ID查找OfferState实体。
     * @param id 要查找的OfferState实体的ID。
     * @return 找到的OfferState实体，如果没有找到则返回null。
     */
    @Query("SELECT * FROM offers WHERE offerId = :id")
    suspend fun findOfferById(id: Long): OfferState?
}

