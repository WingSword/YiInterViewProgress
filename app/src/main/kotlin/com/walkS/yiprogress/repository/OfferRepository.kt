package com.walkS.yiprogress.repository

import com.walkS.yiprogress.db.OfferDao
import com.walkS.yiprogress.state.OfferState
import kotlinx.coroutines.flow.Flow

/**
 * Project YiProgress
 * Created by Wing on 2024/7/25 10:52.
 * Description:
 *
 **/
class OfferRepository(private val offerDao: OfferDao) {
    // 获取所有面试状态的流
    val offers: Flow<List<OfferState>> = offerDao.getAllOffers()

    // 插入新的面试状态
    suspend fun upsertOffer(offer: OfferState): Long {
        try {
            return offerDao.upsertOffer(offer)
        } catch (e: Exception) {
            // 日志记录异常，或者根据业务需求进行其他处理
            println("Error upserting interview: ${e.message}")
            throw e // 可以选择重新抛出异常，或者根据业务需求处理
        }
    }

    // 删除面试状态
    suspend fun deleteOffer(offerState: OfferState) {
        // 检查offerId是否存在
        if (offerDao.offerExists(offerState.offerId)!=null) {
            offerDao.deleteOfferById(offerState.offerId)
        } else {
            // 处理不存在的情况，可能是记录日志或者抛出异常
            println("Attempted to delete non-existent offer: ${offerState.offerId}")
            // 可以选择抛出一个自定义异常，或者根据业务需求处理
        }
    }

    // 批量处理面试状态（示例性添加）
    suspend fun batchUpsertOffer(offers: List<OfferState>): List<Long> {
        // 假设性实现，具体取决于OfferDao是否支持批量操作
        return offers.map { upsertOffer(it) }
    }
}
