package com.freez.cat.catbreed.domain.usecase

interface ToggleFavoriteCatUseCase {

    suspend operator fun invoke(catBreedId: String, isFavorite: Boolean)

}