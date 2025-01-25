package praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeDetails.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.koinViewModel
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeDetails.viewmodel.ExchangeDetailsScreenViewModel

@Composable
fun ExchangeDetailsScreen(
    exchangeId: String,
    viewModel: ExchangeDetailsScreenViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getExchangeDetails(exchangeID = exchangeId)
    }

    ExchangeDetailsScreenContent(
        state = state.value
    )
}