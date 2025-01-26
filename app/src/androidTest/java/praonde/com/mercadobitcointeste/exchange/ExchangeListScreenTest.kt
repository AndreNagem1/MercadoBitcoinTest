package praonde.com.mercadobitcointeste.exchange

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import praonde.com.mercadobitcointeste.common.LoadingEvent
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeData
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.ui.ExchangeListScreenContent

class ExchangeListScreenContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingState() {
        val loadingState: LoadingEvent<List<ExchangeData>> = LoadingEvent.Loading

        composeTestRule.setContent {
            ExchangeListScreenContent(
                state = loadingState,
                onSelectExchange = {}
            )
        }

        composeTestRule.onNode(hasTestTag("LoadingScreen")).assertIsDisplayed()
    }

    @Test
    fun testSuccessState() {
        val exchangeList = listOf(
            ExchangeData("1", "Exchange A", volumePerDayUsd = 0.0),
            ExchangeData("2", "Exchange B", volumePerDayUsd = 0.0)
        )
        val successState = LoadingEvent.Success(exchangeList)

        composeTestRule.setContent {
            ExchangeListScreenContent(
                state = successState,
                onSelectExchange = {}
            )
        }

        composeTestRule.onNodeWithText("Exchange A").assertIsDisplayed()
        composeTestRule.onNodeWithText("Exchange B").assertIsDisplayed()
    }

    @Test
    fun testOnSelectExchangeCalled() {
        val exchangeList = listOf(
            ExchangeData("1", "Exchange A", volumePerDayUsd = 0.0),
            ExchangeData("2", "Exchange B", volumePerDayUsd = 0.0)
        )
        val successState = LoadingEvent.Success(exchangeList)

        var selectedExchangeId = ""
        composeTestRule.setContent {
            ExchangeListScreenContent(
                state = successState,
                onSelectExchange = { selectedExchangeId = it }
            )
        }

        composeTestRule.onNodeWithText("Exchange A").performClick()
        assert(selectedExchangeId == "1")
    }

    @Test
    fun testErrorState() {
        val errorState = LoadingEvent.Error(Throwable("Error"))

        composeTestRule.setContent {
            ExchangeListScreenContent(
                state = errorState,
                onSelectExchange = {}
            )
        }

        composeTestRule.onNode(hasTestTag("ErrorScreen")).assertIsDisplayed()
    }
}
