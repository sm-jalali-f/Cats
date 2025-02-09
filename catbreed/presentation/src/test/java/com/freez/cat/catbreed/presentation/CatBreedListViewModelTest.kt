package com.freez.cat.catbreed.presentation
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.usecase.CatBreedListUseCase
import com.freez.cat.catbreed.domain.usecase.ToggleFavoriteCatUseCase
import com.freez.cat.catbreed.presentation.catBreedList.CatBreedListViewModel
import com.freez.cat.core.util.Image
import com.freez.cat.core.util.Resource
import com.freez.cat.core.util.Weight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CatBreedListViewModelTest {

    private lateinit var viewModel: CatBreedListViewModel

    @Mock
    private lateinit var catBreedListUseCase: CatBreedListUseCase

    @Mock
    private lateinit var toggleFavoriteCatUseCase: ToggleFavoriteCatUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)

        viewModel = CatBreedListViewModel(catBreedListUseCase, toggleFavoriteCatUseCase)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `test catList is updated correctly`() = runTest {
        // Mock data
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
        val flow = flowOf(Resource.Success(mockCatBreeds))

        whenever(catBreedListUseCase(0)).thenReturn(flow)

        viewModel.loadMoreData()

        advanceUntilIdle()

        val result = viewModel.catList.value
        assertEquals(mockCatBreeds, result)
    }

    @Test
    fun `test errorState is updated when error occurs`() = runTest {
        val errorMessage = "Failed to load data"
        val flow = flowOf(Resource.Error<List<CatBreed>>(errorMessage))

        whenever(catBreedListUseCase(0)).thenReturn(flow)

        viewModel.loadMoreData()

        advanceUntilIdle()

        val result = viewModel.errorState.value
        assertEquals(errorMessage, result)
    }

    @Test
    fun `test loadingState is set correctly`() = runTest {
        val flow = flowOf(Resource.Loading<List<CatBreed>>())

        whenever(catBreedListUseCase(0)).thenReturn(flow)

        viewModel.loadMoreData()

        advanceUntilIdle()

        val result = viewModel.loadingState.value
        assertEquals(true, result)
    }

}
