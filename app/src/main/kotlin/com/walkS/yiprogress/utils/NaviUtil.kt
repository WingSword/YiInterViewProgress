package com.walkS.yiprogress.utils

import androidx.navigation.NavBackStackEntry
import com.walkS.yiprogress.entry.Profile

// 增加一个类型安全的路由解析函数
fun NavBackStackEntry.toSafeRoute(): Profile? {
    // 这里应该包含解析逻辑，确保安全和类型安全
    // 例如，检查backStackEntry是否能正确转换为Profile
    // 这里简单假设总是成功转换，实际应用中需要更复杂的逻辑
    return try {
        val route = this.destination.route
        if (route is String && route.startsWith("profile/")) {
            Profile.fromRoute(route.substring("profile/".length))
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}



//