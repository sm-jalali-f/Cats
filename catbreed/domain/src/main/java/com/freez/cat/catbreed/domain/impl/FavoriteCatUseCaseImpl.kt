package com.freez.cat.catbreed.domain.impl

import com.freez.cat.catbreed.domain.FavoriteCatUseCase
import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import javax.inject.Inject

class FavoriteCatUseCaseImpl @Inject constructor(
    private val favoriteCatRepository: FavoriteCatRepository
) : FavoriteCatUseCase {
    override suspend fun changeCatFavorite(catBreedId: String, isFavorite: Boolean) {
        if (isFavorite)
            favoriteCatRepository.setFavorite(catBreedId)
        else
            favoriteCatRepository.removeFavorite(catBreedId)
    }
}