package com.freez.cats.di

import com.freez.cat.catbreed.domain.impl.CatBreedDetailUseCaseImpl
import com.freez.cat.catbreed.domain.impl.GetCatBreedUseCaseImpl
import com.freez.cat.catbreed.domain.impl.ToggleFavoriteCatUseCaseImpl
import com.freez.cat.catbreed.domain.repository.CatBreedDetailRepository
import com.freez.cat.catbreed.domain.repository.CombineCatFavoriteRepository
import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import com.freez.cat.catbreed.domain.usecase.CatBreedDetailUseCase
import com.freez.cat.catbreed.domain.usecase.CatBreedListUseCase
import com.freez.cat.catbreed.domain.usecase.ToggleFavoriteCatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CatDomainModule {
    @Provides
    fun provideCatListListUseCase(
        repository: CombineCatFavoriteRepository
    ): CatBreedListUseCase {
        return GetCatBreedUseCaseImpl(repository)
    }

    @Provides
    fun provideFavoriteCatUseCase(favoriteCatRepository: FavoriteCatRepository): ToggleFavoriteCatUseCase {
        return ToggleFavoriteCatUseCaseImpl(favoriteCatRepository)
    }

    @Provides
    fun provideCatBreedDetailUseCase(catBreedDetailRepository: CatBreedDetailRepository): CatBreedDetailUseCase {
        return CatBreedDetailUseCaseImpl(catBreedDetailRepository)
    }
}