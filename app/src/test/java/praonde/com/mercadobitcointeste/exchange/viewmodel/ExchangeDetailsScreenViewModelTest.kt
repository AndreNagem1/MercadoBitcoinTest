package praonde.com.mercadobitcointeste.exchange.viewmodel

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import praonde.com.mercadobitcointeste.common.LoadingEvent
import praonde.com.mercadobitcointeste.exchangeList.domain.model.exchangeDetailsNotMapped
import praonde.com.mercadobitcointeste.exchangeList.domain.repository.ExchangeRepository
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeDetails.viewmodel.ExchangeDetailsScreenViewModel

@ExperimentalCoroutinesApi
class ExchangeDetailsScreenViewModelTest {

    private lateinit var repository: ExchangeRepository
    private lateinit var viewModel: ExchangeDetailsScreenViewModel

    @Before
    fun setUp() {
        repository = mockk()
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `state starts with LoadingEvent`() = runTest {
        val loadingEvent = LoadingEvent.Loading
        coEvery { repository.getExchangeList() } returns flowOf(loadingEvent)

        viewModel = ExchangeDetailsScreenViewModel(repository)

        viewModel.state.test {
            assertEquals(loadingEvent, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getExchangeDetails should update state when repository returns data`() = runTest {
        val exchangeID = "123"
        val successState = LoadingEvent.Success(exchangeDetailsNotMapped())

        coEvery { repository.getExchangeDetails(any()) } returns flow {
            emit(successState)
        }

        viewModel = ExchangeDetailsScreenViewModel(repository)
        viewModel.getExchangeDetails(exchangeID)

        viewModel.state.test {
            assertEquals(successState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getExchangeDetails should update state when repository returns error`() = runTest {
        val exchangeID = "123"
        val errorState = LoadingEvent.Error(Throwable())

        coEvery { repository.getExchangeDetails(any()) } returns flow {
            emit(errorState)
        }

        viewModel = ExchangeDetailsScreenViewModel(repository)
        viewModel.getExchangeDetails(exchangeID)

        viewModel.state.test {
            assertEquals(errorState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
