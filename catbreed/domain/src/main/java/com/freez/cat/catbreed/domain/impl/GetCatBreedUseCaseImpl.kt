package com.freez.cat.catbreed.domain.impl

import com.freez.cat.catbreed.domain.GetCatBreedUseCase
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedRepository
import com.freez.cat.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatBreedUseCaseImpl @Inject constructor(
    private val breedRepository: CatBreedRepository
) : GetCatBreedUseCase {
    override suspend fun execute(page: Int): Flow<Resource<List<CatBreed>>> {
        return breedRepository.getCatBreeds(page)
    }

}