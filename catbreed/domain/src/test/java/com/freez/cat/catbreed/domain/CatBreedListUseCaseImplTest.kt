package com.freez.cat.catbreed.domain

import com.freez.cat.catbreed.domain.impl.CatBreedListUseCaseImpl
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CombineCatFavoriteRepository
import com.freez.cat.core.util.Image
import com.freez.cat.core.util.Resource
import com.freez.cat.core.util.Weight
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CatBreedListUseCaseImplTest {

    private val repository: CombineCatFavoriteRepository = mock()

    @Test
    fun `getCatBreeds returns list of cat breeds successfully`() = runTest {
        val mockCatBreeds = listOf(
            CatBreed(
                id = "1",
                name = "Siamese",
                isFavorite = false,
                image = Image(url = "url1", id = "id1", width = 100, height = 100),
                origin = "Thailand",
                countryCode = "TH",
                temperament = listOf("Friendly"),
                description = "Description",
                weight = Weight("8-10", "4-5"),
                intelligence = 5
            ),
            CatBreed(
                id = "2",
                name = "Persian",
                isFavorite = false,
                image = Image(url = "url2", id = "id2", width = 100, height = 100),
                origin = "Iran",
                countryCode = "IR",
                temperament = listOf("Calm"),
                description = "Description",
                weight = Weight("10-12", "5-6"),
                intelligence = 4
            )
        )

        whenever(repository.getCatBreeds(0)).thenReturn(flowOf(Resource.Success(mockCatBreeds)))

        val useCase = CatBreedListUseCaseImpl(repository)

        val result = useCase.invoke(0).first()

        Assert.assertTrue(result is Resource.Success)
        assertEquals(mockCatBreeds, result.data)
    }

    @Test
    fun `getCatBreeds returns error when repository fails`() = runTest {
        val errorMessage = "Network error"
        whenever(repository.getCatBreeds(0)).thenReturn(flowOf(Resource.Error(errorMessage)))

        val useCase = CatBreedListUseCaseImpl(repository)

        val result = useCase.invoke(0).first()

        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)
    }
}
