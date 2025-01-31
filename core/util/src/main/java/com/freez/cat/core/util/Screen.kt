package com.freez.cat.core.util

sealed class Screen(val route: String) {
    data object CatListScreen : Screen("cat_list")
    data object CatDetailScreen : Screen("cat_detail/{catId}") {
        fun createRoute(catId: String) = "video_detail/$catId"
    }
}
