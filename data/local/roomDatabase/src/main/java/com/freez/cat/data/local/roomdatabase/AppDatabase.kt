package com.freez.cat.data.local.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.freez.cat.data.local.roomdatabase.dao.FavoriteCatDao
import com.freez.cat.data.local.roomdatabase.entity.FavoriteCatsEntity

@Database(entities = [FavoriteCatsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCatDao(): FavoriteCatDao
}
