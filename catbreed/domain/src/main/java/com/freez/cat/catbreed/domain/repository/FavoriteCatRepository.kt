package com.freez.cat.catbreed.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FavoriteCatRepository {

    suspend fun isFavorite(catId: String):Flow<Boolean>
    suspend fun setFavorite(catId: String)
    suspend fun removeFavorite(catId: String)
    suspend fun getFavoriteCats(): Flow<List<String>>
}