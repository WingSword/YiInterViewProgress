package com.walkS.yiprogress.state

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offers")
data class OfferState(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "offerId")
    var offerId: Long,
    @ColumnInfo(name = "companyName")
    var companyName: String = "",
    var department: String = "",
    var job: String = "",
    var time: String? = "",
)

data class OfferStateList(
    val isRefreshing: Boolean = false,
    val list: List<OfferState> = emptyList<OfferState>()
)