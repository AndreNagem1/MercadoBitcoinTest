package praonde.com.mercadobitcointeste.exchange.viewmodel

import androidx.compose.runtime.collectAsState
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import praonde.com.mercadobitcointeste.common.LoadingEvent
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeData
import praonde.com.mercadobitcointeste.exchangeList.domain.repository.ExchangeRepository
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.viewmodel.ExchangeListScreenViewModel

class ExchangeListScreenViewModelTest {

    private lateinit var viewModel: ExchangeListScreenViewModel
    private val repository: ExchangeRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        coEvery { repository.getExchangeList() } returns flowOf(LoadingEvent.Loading)
        viewModel = ExchangeListScreenViewModel(repository)
    }

    @Test
    fun `initial state is Loading`() = testScope.runTest {
        viewModel.state.test {
            assertEquals(LoadingEvent.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state updates with success event from repository`() = testScope.runTest {
        val exchangeDetailsList = listOf(
            ExchangeData(id = "test1", name = "test1", volumePerDayUsd = 20.20),
            ExchangeData(id = "test2", name = "test2", volumePerDayUsd = 40.40)
        )
        val successEvent = LoadingEvent.Success(exchangeDetailsList)

        coEvery { repository.getExchangeList() } returns flowOf(successEvent)

        viewModel = ExchangeListScreenViewModel(repository)


        viewModel.state.collect{

        }

        viewModel.state.test {
            assertEquals(LoadingEvent.Loading, awaitItem())
            assertEquals(successEvent, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `state updates with error event from repository`() = testScope.runTest {
        val errorEvent = LoadingEvent.Error(Throwable(message = "Something went wrong"))

        coEvery { repository.getExchangeList() } returns flowOf(errorEvent)

        viewModel = ExchangeListScreenViewModel(repository)

        viewModel.state.test {
            assertEquals(LoadingEvent.Loading, awaitItem())
            assertEquals(errorEvent, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
