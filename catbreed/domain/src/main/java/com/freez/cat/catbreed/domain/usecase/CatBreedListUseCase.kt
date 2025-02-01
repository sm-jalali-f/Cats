package com.freez.cat.catbreed.domain.usecase

import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface CatBreedListUseCase {
    suspend operator fun invoke(page: Int): Flow<Resource<List<CatBreed>>>
}