package com.freez.cat.catbreed.domain

import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetCatBreedUseCase {
    suspend fun execute(page: Int): Flow<Resource<List<CatBreed>>>
}