package com.freez.cat.data.local.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freez.cat.data.local.roomdatabase.entity.FavoriteCatsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteCatsEntity)

    @Query("DELETE FROM favorite_cats WHERE catId = :catId")
    suspend fun remove(catId: String)

    @Query("SELECT catId FROM favorite_cats")
    fun getAllFavoriteCats(): Flow<List<String>>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_cats WHERE catId = :catId)")
     fun isFavorite(catId: String): Flow<Boolean>
}