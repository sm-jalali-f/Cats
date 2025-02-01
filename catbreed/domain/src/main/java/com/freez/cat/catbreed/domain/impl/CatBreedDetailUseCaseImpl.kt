package com.freez.cat.catbreed.domain.impl

import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedDetailRepository
import com.freez.cat.catbreed.domain.usecase.CatBreedDetailUseCase
import com.freez.cat.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatBreedDetailUseCaseImpl @Inject constructor(
    private val repository: CatBreedDetailRepository
) : CatBreedDetailUseCase {
    override suspend fun invoke(catId: String): Flow<Resource<CatBreed>> {
        return repository.getDetail(catId)
    }
}