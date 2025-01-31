package com.freez.cats.di

import com.freez.cat.catbreed.domain.FavoriteCatUseCase
import com.freez.cat.catbreed.domain.GetCatBreedUseCase
import com.freez.cat.catbreed.domain.impl.FavoriteCatUseCaseImpl
import com.freez.cat.catbreed.domain.impl.GetCatBreedUseCaseImpl
import com.freez.cat.catbreed.domain.repository.CatBreedListRepository
import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CatDomainModule {
    @Provides
    fun provideCatListListUseCase(
        catListRepository: CatBreedListRepository,
        favoriteRepository: FavoriteCatRepository,
    ): GetCatBreedUseCase {
        return GetCatBreedUseCaseImpl(catListRepository, favoriteRepository)
    }

    @Provides
    fun provideFavoriteCatUseCase(favoriteCatRepository: FavoriteCatRepository): FavoriteCatUseCase {
        return FavoriteCatUseCaseImpl(favoriteCatRepository)
    }
}