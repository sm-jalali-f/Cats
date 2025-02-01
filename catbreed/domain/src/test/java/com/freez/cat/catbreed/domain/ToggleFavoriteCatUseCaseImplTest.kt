package com.freez.cat.catbreed.domain

import com.freez.cat.catbreed.domain.impl.ToggleFavoriteCatUseCaseImpl
import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ToggleFavoriteCatUseCaseImplTest {

    private val favoriteCatRepository: FavoriteCatRepository = mock()

    @Test
    fun `invoke sets favorite when isFavorite is true`() = runTest {
        val catBreedId = "12345"
        val isFavorite = true

        val useCase = ToggleFavoriteCatUseCaseImpl(favoriteCatRepository)

        useCase.invoke(catBreedId, isFavorite)

        verify(favoriteCatRepository).setFavorite(catBreedId)
    }

    @Test
    fun `invoke removes favorite when isFavorite is false`() = runTest {
        val catBreedId = "12345"
        val isFavorite = false

        val useCase = ToggleFavoriteCatUseCaseImpl(favoriteCatRepository)

        useCase.invoke(catBreedId, isFavorite)

        verify(favoriteCatRepository).removeFavorite(catBreedId)
    }
}
