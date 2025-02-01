package com.freez.cat.catbreed.presentation

import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.catbreed.domain.usecase.CatBreedDetailUseCase
import com.freez.cat.catbreed.presentation.catBreedDetail.CatBreedDetailViewModel
import com.freez.cat.core.util.Image
import com.freez.cat.core.util.Resource
import com.freez.cat.core.util.Weight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CatBreedDetailViewModelTest {

    private lateinit var viewModel: CatBreedDetailViewModel

    @Mock
    private lateinit var catBreedDetailUseCase: CatBreedDetailUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CatBreedDetailViewModel(catBreedDetailUseCase)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test catBreed is updated correctly`() = runTest {
        val catBreed =
            CatBreed(
                id = "nOpQr",
                name = "Bengal",
                isFavorite = false,
                origin = "United States",
                countryCode = "US",
                temperament = "Energetic, Playful, Curious, Intelligent".split(",")
                    .map { it.trim() },
                description = """
                Bengals are known for their distinctive spotted coat and energetic personality.
                 They are playful, curious, and highly intelligent cats.""".trimIndent(),
                weight = Weight(
                    imperial = "8 - 15",
                    metric = "4 - 7"
                ),
                intelligence = 5,
                image = Image(
                    url = "aBcDeFgHi",
                    id = "aBcDeFgHi",
                    width = 800,
                    height = 600,
                )
            )
        val flow = flowOf(Resource.Success(catBreed))

        whenever(catBreedDetailUseCase.invoke("nOpQr")).thenReturn(flow)

        viewModel.getCatBreedDetail("nOpQr")

        advanceUntilIdle() // Wait for the flow to complete and update state

        val result = viewModel.catBreed.value
        println("expected")
        println(catBreed)
        assertEquals(catBreed, result)
    }
}
