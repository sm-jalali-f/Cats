package com.freez.cat.data.local.roomdatabase;

import androidx.room.Database;

@Database(entities = [BookmarkedVideo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkedVideoDao(): BookmarkedVideoDao
}
