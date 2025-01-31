package com.freez.cat.catbreed.domain.impl

import android.util.Log
import com.freez.cat.catbreed.domain.GetCatBreedUseCase
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedListRepository
import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import com.freez.cat.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCatBreedUseCaseImpl @Inject constructor(
    private val breedRepository: CatBreedListRepository,
    private val favoriteCatRepository: FavoriteCatRepository
) : GetCatBreedUseCase {
    override suspend fun execute(page: Int): Flow<Resource<List<CatBreed>>> {
        Log.d("THE-CAT_API", "GetCatBreedUseCaseImpl: ")
        return breedRepository.getCatBreeds(page)

    }

}
//breedList.map { breed ->
//    breed.copy(isFavorite = favoriteList.contains(breed.id))
//}