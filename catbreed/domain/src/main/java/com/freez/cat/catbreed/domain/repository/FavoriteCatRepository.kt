package com.freez.cat.catbreed.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteCatRepository {
    suspend fun changeFavorite(catId: String, favorite: Boolean)
    suspend fun isFavorite(catId: String): Flow<Boolean>
    suspend fun getFavoriteCats(): Flow<List<String>>
}