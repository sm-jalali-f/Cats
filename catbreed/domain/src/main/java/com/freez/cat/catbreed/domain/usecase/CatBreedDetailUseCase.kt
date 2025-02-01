package com.freez.cat.catbreed.domain.usecase

import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface CatBreedDetailUseCase {

    suspend operator fun invoke(catId: String): Flow<Resource<CatBreed>>
}