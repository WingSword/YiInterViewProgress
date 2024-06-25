package com.walkS.yiprogress.entry


enum class Profile(val route: String, val title: String) {
    // 主页路由
    HOME("profile/home", "主页"),

    // 面试路由
    INTERVIEW("profile/interview", "面试列表");

    /**
     * 根据路由字符串解析对应的Profile枚举值。
     * 如果无法解析，返回null。
     *
     * @param route 路由字符串
     * @return 对应的Profile枚举值，如果不存在则为null
     */
    companion object {
        fun fromRoute(route: String?): Profile? {
            return values().firstOrNull { it.route == route }
        }
    }

    /**
     * 根据当前枚举值生成完整的URL。
     *
     * 假设存在一个基础URL，此方法可以方便地生成带前缀的完整URL。
     * 这个功能假设了枚举值的设计可能与URL路径直接相关。
     *
     * @param baseUrl 基础URL
     * @return 完整的URL
     */
    fun generateFullUrl(baseUrl: String): String {
        return "$baseUrl$route"
    }
}

