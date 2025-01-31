package com.freez.cats.di

import com.freez.cat.catbreed.domain.repository.CatBreedListRepository
import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import com.freez.cat.catbreed.repository.CatBreedListRepositoryImpl
import com.freez.cat.catbreed.repository.FavoriteCatRepositoryImpl
import com.freez.cat.data.local.roomdatabase.dao.FavoriteCatDao
import com.freez.cat.data.remote.theCatApi.CatApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideCatListRepository(apiService: CatApiService): CatBreedListRepository {
        return CatBreedListRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(favoriteCatDao: FavoriteCatDao): FavoriteCatRepository {
        return FavoriteCatRepositoryImpl(favoriteCatDao)
    }

}