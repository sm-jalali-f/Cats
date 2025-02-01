package com.freez.cat.core.util

sealed class Screen(val route: String) {
    data object CatListScreen : Screen("cat_list")
    data object CatDetailScreen : Screen("cat_detail/{catId}/{imageUrl}") {
        fun createRoute(catId: String,imageUrl: String) = "cat_detail/$catId/$imageUrl"
    }
}
