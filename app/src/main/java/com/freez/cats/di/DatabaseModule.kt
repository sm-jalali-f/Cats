package com.freez.cats.di

import android.content.Context
import androidx.room.Room
import com.freez.cat.data.local.roomdatabase.AppDatabase
import com.freez.cat.data.local.roomdatabase.dao.FavoriteCatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "cat_database",
        ).build()
    }

    @Provides
    fun provideFavoriteCatDao(database: AppDatabase): FavoriteCatDao {
        return database.favoriteCatDao()
    }
}