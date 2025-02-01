package com.freez.cats.di

import com.freez.cat.catbreed.domain.repository.CatBreedDetailRepository
import com.freez.cat.catbreed.domain.repository.CatBreedListRepository
import com.freez.cat.catbreed.domain.repository.CombineCatFavoriteRepository
import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import com.freez.cat.catbreed.repository.CatBreedDetailRepositoryImpl
import com.freez.cat.catbreed.repository.CatBreedListRepositoryImpl
import com.freez.cat.catbreed.repository.CombineCatFavoriteRepositoryImpl
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

    @Provides
    @Singleton
    fun provideCombineRepository(
        catBreedListRepository: CatBreedListRepository,
        favoriteCatRepository: FavoriteCatRepository
    ): CombineCatFavoriteRepository {
        return CombineCatFavoriteRepositoryImpl(catBreedListRepository, favoriteCatRepository)
    }

    @Provides
    @Singleton
    fun provideCatBreedDetailRepository(
        apiService: CatApiService
    ): CatBreedDetailRepository {
        return CatBreedDetailRepositoryImpl(apiService)
    }


}