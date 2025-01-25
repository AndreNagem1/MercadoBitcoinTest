package praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.koinViewModel
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.viewmodel.ExchangeListScreenViewModel

@Composable
fun ExchangeListScreen(
    viewModel: ExchangeListScreenViewModel = koinViewModel(),
    navigateToDetailsScreen: (String) -> Unit
) {
    val state = viewModel.state.collectAsState()

    ExchangeListScreenContent(
        state = state.value,
        onSelectExchange = { navigateToDetailsScreen(it) }
    )
}
