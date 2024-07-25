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
    var time: String? = "",//入职时间
    val workLocation: String = "", //工作地点
    val teamSize: Int = 0,//团队人数
    val salary: Double = 0.0,//工资
    val yearEndBonusMonths: Double = 0.0,//年终奖金
    val allowances: Double = 0.0,//补贴每月
    val annualPackagePreTax: Double = (allowances + salary) * 12 + yearEndBonusMonths,//年总包（税前）
    val socialInsuranceBase: Double = salary,//社保缴纳基数
    val housingFundBase: Double = salary,//公积金缴纳基数
    val socialInsuranceRate: Float = 0.12f,//公积金缴纳比例
    val supplementaryHousingFund: Double = 0.0,//补充公积金
    val housingFund: Double = (housingFundBase * socialInsuranceRate) * 2,//公积金
    val monthlyNetSalary: Double = 0.0,//月到手工资
    val monthlyIncome: Double = 0.0,//等价月收入
    val workingHours: String = "",//工作时间
    val overtimeIntensity: String = "",//加班强度
    val businessTripFrequency: String = "",//出差频率
    val professionalMatch: Boolean = false,//与专业是否匹配
    val careerDevelopmentHelp: Boolean = false,//对后续职业发展是否有帮助
    val promotionPotential: Boolean = false,//晋升可能性
    val companySizeAndInfluence: String = "",//规模、行业影响力
    val futureProspects: String = "",//未来发展可能性
    val otherDetails: String = "",//其他
    val additionalInformation: String = ""//附加条件
)

data class OfferStateList(
    val isRefreshing: Boolean = false,
    val list: List<OfferState> = emptyList<OfferState>()
)