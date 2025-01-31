package com.freez.cat.catbreed.repository

import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import com.freez.cat.data.local.roomdatabase.dao.FavoriteCatDao
import com.freez.cat.data.local.roomdatabase.entity.FavoriteCatsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteCatRepositoryImpl @Inject constructor(
    private val favoriteCatDao: FavoriteCatDao
) : FavoriteCatRepository {

    override suspend fun isFavorite(catId: String): Flow<Boolean> {
        return favoriteCatDao.isFavorite(catId)
    }

    override suspend fun setFavorite(catId: String) {
        favoriteCatDao.insert(
            FavoriteCatsEntity(
                catId = catId,
                id = null
            )
        )
    }

    override suspend fun removeFavorite(catId: String) {
        favoriteCatDao.remove(catId)
    }

    override suspend fun getFavoriteCats(): Flow<List<String>> {
        return favoriteCatDao.getAllFavoriteCats()
    }
}