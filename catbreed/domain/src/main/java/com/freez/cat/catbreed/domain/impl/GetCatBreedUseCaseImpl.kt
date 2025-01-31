package com.freez.cat.catbreed.domain.impl

import android.util.Log
import com.freez.cat.catbreed.domain.GetCatBreedUseCase
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedListRepository
import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import com.freez.cat.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCatBreedUseCaseImpl @Inject constructor(
    private val breedRepository: CatBreedListRepository,
    private val favoriteCatRepository: FavoriteCatRepository
) : GetCatBreedUseCase {
    override suspend fun execute(page: Int): Flow<Resource<List<CatBreed>>> {
        val catBreedsFlow = breedRepository.getCatBreeds(page)

        val favoriteCatIdsFlow = favoriteCatRepository.getFavoriteCats()
        var temp = combine(catBreedsFlow, favoriteCatIdsFlow) { catBreedsResource, favoriteCatIds ->
            Log.d("GetCatBreedUseCaseImpl", "execute: ")
            when (catBreedsResource) {
                is Resource.Success -> {

                    Resource.Success(catBreedsResource.data!!.map { catBreed ->
                        catBreed.copy(
                            isFavorite = favoriteCatIds.contains(catBreed.id)
                        )
                    }
                    )
                }

                is Resource.Error -> {
                    return@combine catBreedsResource
                }

                is Resource.Loading -> {
                    Resource.Loading()
                }

            }
        }
        return temp
    }

}