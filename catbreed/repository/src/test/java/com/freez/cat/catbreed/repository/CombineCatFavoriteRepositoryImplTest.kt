package com.freez.cat.catbreed.repository

import android.util.Log
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.repository.CatBreedListRepository
import com.freez.cat.catbreed.domain.repository.FavoriteCatRepository
import com.freez.cat.core.util.Image
import com.freez.cat.core.util.Resource
import com.freez.cat.core.util.Weight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CombineCatFavoriteRepositoryImplTest {

    @Mock
    private lateinit var catBreedListRepository: CatBreedListRepository

    @Mock
    private lateinit var favoriteCatRepository: FavoriteCatRepository

    private lateinit var combineCatFavoriteRepository: CombineCatFavoriteRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        combineCatFavoriteRepository = CombineCatFavoriteRepositoryImpl(
            catBreedListRepository,
            favoriteCatRepository
        )
    }

    @Test
    fun `getCatBreeds combines cat breeds and favorites successfully`() = runTest {
        val mockCatBreeds = listOf(
            CatBreed(
                id = "j3kLp",
                name = "Cornish Rex",
                isFavorite = false,
                image = Image(
                    url = "aBcDeFgHi",
                    id = "aBcDeFgHi",
                    width = 800,
                    height = 600,
                ),
                origin = "Cornwall, England",
                countryCode = "GB",
                temperament = "Intelligent, Curious, Playful, Affectionate".split(",")
                    .map { it.trim() },
                description = "The Cornish Rex has a unique, wavy coat and a slender body. They are known for their playful and curious nature, often enjoying interactive games and exploring their surroundings.",
                weight = Weight(
                    imperial = "6 - 10",
                    metric = "3 - 5"
                ),
                intelligence = 4
            ),

            CatBreed(
                id = "xYzWv",
                name = "Scottish Fold",
                isFavorite = false,
                image = Image(
                    url = "qRsTuVwX",
                    id = "qRsTuVwX",
                    width = 1024,
                    height = 768,
                ),
                origin = "Scotland",
                countryCode = "UK",
                temperament = "Affectionate, Calm, Loyal, Sweet".split(",")
                    .map { it.trim() },
                description = "Scottish Folds are easily recognized by their folded ears. They are generally calm and affectionate cats, known for their loyalty and sweet disposition.",
                weight = Weight(
                    imperial = "8 - 13",
                    metric = "4 - 6"
                ),
                intelligence = 3
            ),
            CatBreed(
                id = "nOpQr",
                name = "Bengal",
                isFavorite = false,
                image = Image(
                    url = "lKmNoPqR",
                    id = "lKmNoPqR",
                    width = 1920,
                    height = 1080,
                ),
                origin = "United States",
                countryCode = "US",
                temperament = "Energetic, Playful, Curious, Intelligent".split(",")
                    .map { it.trim() },
                description = "Bengals are known for their distinctive spotted coat and energetic personality. They are playful, curious, and highly intelligent cats.",
                weight = Weight(
                    imperial = "8 - 15",
                    metric = "4 - 7"
                ),
                intelligence = 5
            )
        )
        val mockFavoriteCats = listOf("j3kLp")
        val expectedCatBreeds = listOf(
            CatBreed(
                id = "j3kLp",
                name = "Cornish Rex",
                isFavorite = true,
                image = Image(
                    url = "aBcDeFgHi",
                    id = "aBcDeFgHi",
                    width = 800,
                    height = 600,
                ),
                origin = "Cornwall, England",
                countryCode = "GB",
                temperament = "Intelligent, Curious, Playful, Affectionate".split(",")
                    .map { it.trim() },
                description = "The Cornish Rex has a unique, wavy coat and a slender body. They are known for their playful and curious nature, often enjoying interactive games and exploring their surroundings.",
                weight = Weight(
                    imperial = "6 - 10",
                    metric = "3 - 5"
                ),
                intelligence = 4
            ),

            CatBreed(
                id = "xYzWv",
                name = "Scottish Fold",
                isFavorite = false,
                image = Image(
                    url = "qRsTuVwX",
                    id = "qRsTuVwX",
                    width = 1024,
                    height = 768,
                ),
                origin = "Scotland",
                countryCode = "UK",
                temperament = "Affectionate, Calm, Loyal, Sweet".split(",")
                    .map { it.trim() },
                description = "Scottish Folds are easily recognized by their folded ears. They are generally calm and affectionate cats, known for their loyalty and sweet disposition.",
                weight = Weight(
                    imperial = "8 - 13",
                    metric = "4 - 6"
                ),
                intelligence = 3
            ),
            CatBreed(
                id = "nOpQr",
                name = "Bengal",
                isFavorite = false,
                image = Image(
                    url = "lKmNoPqR",
                    id = "lKmNoPqR",
                    width = 1920,
                    height = 1080,
                ),
                origin = "United States",
                countryCode = "US",
                temperament = "Energetic, Playful, Curious, Intelligent".split(",")
                    .map { it.trim() },
                description = "Bengals are known for their distinctive spotted coat and energetic personality. They are playful, curious, and highly intelligent cats.",
                weight = Weight(
                    imperial = "8 - 15",
                    metric = "4 - 7"
                ),
                intelligence = 5
            )
        )

        whenever(catBreedListRepository.getCatBreeds(0)).thenReturn(
            flowOf(
                Resource.Success(
                    mockCatBreeds
                )
            )
        )
        whenever(favoriteCatRepository.getFavoriteCats()).thenReturn(flowOf(mockFavoriteCats))

        val result = combineCatFavoriteRepository.getCatBreeds(0)
        println(result.last().data)

        assertEquals(expectedCatBreeds, result.last().data)
    }

    @Test
    fun `getCatBreeds handles error correctly`() = runTest {
        // Mock the error response from catBreedListRepository
        whenever(catBreedListRepository.getCatBreeds(0)).thenReturn(flowOf(Resource.Error("Network Error")))
        whenever(favoriteCatRepository.getFavoriteCats()).thenReturn(flowOf(emptyList()))

        // Run the flow and collect the results
        val result = combineCatFavoriteRepository.getCatBreeds(0).toList()

        // Check the result
        assertTrue(result.last() is Resource.Error)
        assertEquals("Network Error", (result.last() as Resource.Error).message)
    }
}
