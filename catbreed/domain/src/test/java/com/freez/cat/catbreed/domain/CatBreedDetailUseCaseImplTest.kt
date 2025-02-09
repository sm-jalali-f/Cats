package com.freez.cat.catbreed.domain

import com.freez.cat.catbreed.domain.impl.CatBreedDetailUseCaseImpl
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedDetailRepository
import com.freez.cat.core.util.Image
import com.freez.cat.core.util.Resource
import com.freez.cat.core.util.Weight
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify

class CatBreedDetailUseCaseImplTest {

    private val catBreedDetailRepository: CatBreedDetailRepository = mock()

    @Test
    fun `invoke calls repository getDetail and returns correct data`() = runTest {
        val catId = "12345"
        val mockCatBreed = CatBreed(
            id = catId,
            name = "Bengal",
            isFavorite = false,
            image = Image(
                url = "some_url",
                id = "some_id",
                width = 500,
                height = 500
            ),
            origin = "US",
            countryCode = "US",
            temperament = listOf("Energetic", "Curious"),
            description = "A playful and energetic cat",
            weight = Weight("8-15", "4-7"),
            intelligence = 5
        )
        val mockResource = Resource.Success(mockCatBreed)

        whenever(catBreedDetailRepository.getDetail(catId)).thenReturn(flowOf(mockResource))

        val useCase = CatBreedDetailUseCaseImpl(catBreedDetailRepository)

        val result = useCase(catId)

        var resultResource: Resource<CatBreed>? = null
        result.collect { resultResource = it }

        verify(catBreedDetailRepository).getDetail(catId)

        assertEquals(mockResource, resultResource)
    }
}
