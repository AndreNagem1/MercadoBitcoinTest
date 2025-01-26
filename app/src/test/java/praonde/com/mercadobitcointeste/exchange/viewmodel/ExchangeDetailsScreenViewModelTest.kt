package praonde.com.mercadobitcointeste.exchange.viewmodel

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import praonde.com.mercadobitcointeste.common.LoadingEvent
import praonde.com.mercadobitcointeste.exchangeList.domain.model.exchangeDetailsNotMapped
import praonde.com.mercadobitcointeste.exchangeList.domain.repository.ExchangeRepository
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeDetails.viewmodel.ExchangeDetailsScreenViewModel

@ExperimentalCoroutinesApi
class ExchangeDetailsScreenViewModelTest {

    private lateinit var viewModel: ExchangeDetailsScreenViewModel
    private val mockRepository: ExchangeRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ExchangeDetailsScreenViewModel(mockRepository)
    }

    @Test
    fun `getExchangeDetails should update state when repository returns data`() = runTest {
        val exchangeID = "123"
        val mockDetails = exchangeDetailsNotMapped()

        coEvery { mockRepository.getExchangeDetails(exchangeID) } returns flowOf(
            LoadingEvent.Success(mockDetails)
        )

        viewModel.getExchangeDetails(exchangeID)

        val result = viewModel.state.value

        assertTrue(result is LoadingEvent.Success)
        assertEquals(mockDetails, (result as LoadingEvent.Success).data)
    }

    @Test
    fun `getExchangeDetails should handle error state when repository returns error`() = runTest {
        val exchangeID = "123"
        val error = Throwable("Network error")

        coEvery { mockRepository.getExchangeDetails(exchangeID) } returns MutableStateFlow(
            LoadingEvent.Error(error)
        )

        viewModel.getExchangeDetails(exchangeID)

        val result = viewModel.state.value

        assertTrue(result is LoadingEvent.Error)
        assertEquals(error, (result as LoadingEvent.Error))
    }

    @Test
    fun `getExchangeDetails should set state to loading initially`() = runTest {
        val exchangeID = "123"

        // Trigger ViewModel action before repository mock is set up
        viewModel.getExchangeDetails(exchangeID)

        // Collect the state
        val result = viewModel.state.value

        // Verify initial loading state
        assertTrue(result is LoadingEvent.Loading)
    }

    @Test
    fun `getExchangeDetails should update state when repository returns success after loading`() =
        runTest {
            val exchangeID = "123"
            val mockDetails = exchangeDetailsNotMapped()

            coEvery { mockRepository.getExchangeDetails(exchangeID) } returns MutableStateFlow(
                LoadingEvent.Loading
            )

            viewModel.getExchangeDetails(exchangeID)

            coEvery { mockRepository.getExchangeDetails(exchangeID) } returns MutableStateFlow(
                LoadingEvent.Success(mockDetails)
            )

            advanceUntilIdle()

            val result = viewModel.state.value
            assertTrue(result is LoadingEvent.Success)
            assertEquals(mockDetails, (result as LoadingEvent.Success).data)
        }
}
