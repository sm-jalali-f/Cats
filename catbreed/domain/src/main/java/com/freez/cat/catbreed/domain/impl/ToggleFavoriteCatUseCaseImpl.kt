package com.freez.cat.catbreed.domain.impl

import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import com.freez.cat.catbreed.domain.usecase.ToggleFavoriteCatUseCase
import javax.inject.Inject

class ToggleFavoriteCatUseCaseImpl @Inject constructor(
    private val favoriteCatRepository: FavoriteCatRepository
) : ToggleFavoriteCatUseCase {
    override suspend fun invoke(catBreedId: String, isFavorite: Boolean) {
        if (isFavorite)
            favoriteCatRepository.setFavorite(catBreedId)
        else
            favoriteCatRepository.removeFavorite(catBreedId)
    }
}