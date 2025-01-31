package com.freez.cat.catbreed.domain

interface FavoriteCatUseCase {

    suspend fun changeCatFavorite(catBreedId: String, isFavorite: Boolean)

}