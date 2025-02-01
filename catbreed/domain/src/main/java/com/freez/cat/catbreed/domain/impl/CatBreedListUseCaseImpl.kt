package com.freez.cat.catbreed.domain.impl

import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CombineCatFavoriteRepository
import com.freez.cat.catbreed.domain.usecase.CatBreedListUseCase
import com.freez.cat.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatBreedListUseCaseImpl @Inject constructor(
    private val repository: CombineCatFavoriteRepository,
) : CatBreedListUseCase {
    override suspend fun invoke(page: Int): Flow<Resource<List<CatBreed>>> {
        return repository.getCatBreeds(page)
    }

}