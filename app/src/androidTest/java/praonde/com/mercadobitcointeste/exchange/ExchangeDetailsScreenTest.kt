package praonde.com.mercadobitcointeste.exchange

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import praonde.com.mercadobitcointeste.common.network.LoadingEvent
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeDetails
import praonde.com.mercadobitcointeste.exchangeList.domain.model.exchangeDetailsNotMapped
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeDetails.ui.ExchangeDetailsScreenContent

class ExchangeDetailsScreenContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingState() {
        val loadingState = LoadingEvent.Loading

        composeTestRule.setContent {
            ExchangeDetailsScreenContent(state = loadingState)
        }

        composeTestRule.onNode(hasTestTag("LoadingScreen")).assertIsDisplayed()
    }

    @Test
    fun testSuccessState() {
        val exchangeDetails = ExchangeDetails(
            exchangeId = "1",
            name = "Exchange A",
            website = "https://exchangeA.com",
            volume1dayUsd = 1000.0,
            volume1hrsUsd = 200.0,
            volume1mthUsd = 5000.0,
            rank = 0
        )
        val successState = LoadingEvent.Success(exchangeDetails)

        composeTestRule.setContent {
            ExchangeDetailsScreenContent(state = successState)
        }

        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Exchange A").assertIsDisplayed()
        composeTestRule.onNodeWithText("https://exchangeA.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("$1,000.00").assertIsDisplayed()
        composeTestRule.onNodeWithText("$200.00").assertIsDisplayed()
        composeTestRule.onNodeWithText("$5,000.00").assertIsDisplayed()
    }

    @Test
    fun testSuccessStateWithNullName() {
        val exchangeDetails = ExchangeDetails(
            exchangeId = "1",
            name = null,
            website = "https://exchangeA.com",
            volume1dayUsd = 1000.0,
            volume1hrsUsd = 200.0,
            volume1mthUsd = 5000.0,
            rank = 1
        )
        val successState = LoadingEvent.Success(exchangeDetails)

        composeTestRule.setContent {
            ExchangeDetailsScreenContent(state = successState)
        }

        composeTestRule.onNodeWithText("Sem nome").assertIsDisplayed()
    }

    @Test
    fun testSuccessStateWithNotMapped() {
        val successState = LoadingEvent.Success(exchangeDetailsNotMapped())

        composeTestRule.setContent {
            ExchangeDetailsScreenContent(state = successState)
        }

        composeTestRule.onAllNodesWithText("Not Mapped")[0].assertIsDisplayed()
    }

    @Test
    fun testErrorState() {
        val errorState = LoadingEvent.Error(Throwable("Error"))

        composeTestRule.setContent {
            ExchangeDetailsScreenContent(state = errorState)
        }

        composeTestRule.onNode(hasTestTag("ErrorScreen")).assertIsDisplayed()
    }
}
