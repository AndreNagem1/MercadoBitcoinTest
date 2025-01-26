import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import praonde.com.mercadobitcointeste.common.LoadingEvent
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeData
import praonde.com.mercadobitcointeste.exchangeList.domain.repository.ExchangeRepository
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.viewmodel.ExchangeListScreenViewModel

@ExperimentalCoroutinesApi
class ExchangeListScreenViewModelTest {

    private lateinit var repository: ExchangeRepository
    private lateinit var viewModel: ExchangeListScreenViewModel

    @Before
    fun setUp() {
        repository = mockk()
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `state starts with LoadingEvent`() = runTest {
        val loadingEvent = LoadingEvent.Loading
        coEvery { repository.getExchangeList() } returns flowOf(loadingEvent)

        viewModel = ExchangeListScreenViewModel(repository)

        viewModel.state.test {
            assertEquals(loadingEvent, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state updates with success event from repository`() = runTest {
        val successEvent = LoadingEvent.Success(
            data = listOf(
                ExchangeData(
                    id = "",
                    name = null,
                    volumePerDayUsd = 0.0
                )
            )
        )
        coEvery { repository.getExchangeList() } returns flow {
            emit(successEvent)
        }.flowOn(Dispatchers.IO)

        viewModel = ExchangeListScreenViewModel(repository)

        viewModel.state.test {
            assertEquals(successEvent, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state updates with error event from repository`() = runTest {
        val errorEvent = LoadingEvent.Error(Throwable(message = "Something went wrong"))
        coEvery { repository.getExchangeList() } returns flow<LoadingEvent<List<ExchangeData>>> {
            emit(errorEvent)
        }.flowOn(Dispatchers.IO)

        viewModel = ExchangeListScreenViewModel(repository)

        viewModel.state.test {
            assertEquals(errorEvent, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
