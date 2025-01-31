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
    fun insertBookmark(bookmark: FavoriteCatsEntity)

    @Query("DELETE FROM bookmarked_videos WHERE videoId = :videoId")
    fun removeBookmark(videoId: Long)

    @Query("SELECT * FROM bookmarked_videos")
    fun getAllBookmarks(): Flow<List<BookmarkedVideo>>

    @Query("SELECT EXISTS (SELECT 1 FROM bookmarked_videos WHERE videoId = :videoId)")
    fun isBookmarked(videoId: Long): Flow<Boolean>
}