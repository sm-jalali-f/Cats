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
    fun insert(favorite: FavoriteCatsEntity)

    @Query("DELETE FROM favorite_cats WHERE catId = :catId")
    fun remove(catId: String)

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_cats WHERE catId = :catId)")
    fun isFavorite(catId: String): Flow<Boolean>

    @Query("SELECT * FROM favorite_cats")
    fun getAllFavoriteCats(): Flow<List<String>>
}