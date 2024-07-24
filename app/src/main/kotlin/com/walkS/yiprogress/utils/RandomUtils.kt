package com.walkS.yiprogress.utils

/**
 * Project YiProgress
 * Created by Wing on 2024/7/24 15:15.
 * Description:
 *
 **/

object RandomUtils {
    const val OFFER_ID_HEAD = "333"
    const val INTERVIEW_ID_HEAD = "222"
    fun optOfferRandomId(): Long {
        return (OFFER_ID_HEAD + (Math.random() * 1000000).toLong()).toLong()
    }

    fun optInterViewRandomId(): Long {
        return (INTERVIEW_ID_HEAD + (Math.random() * 1000000).toLong()).toLong()
    }
}

