package praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeDetails.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import praonde.com.mercadobitcointeste.R
import praonde.com.mercadobitcointeste.common.network.LoadingEvent
import praonde.com.mercadobitcointeste.common.composables.BaseScreen
import praonde.com.mercadobitcointeste.common.extensions.toDollarCurrency
import praonde.com.mercadobitcointeste.common.network.getSuccessDataOrNull
import praonde.com.mercadobitcointeste.common.network.isError
import praonde.com.mercadobitcointeste.common.network.isLoading
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeDetails

@Composable
fun ExchangeDetailsScreenContent(state: LoadingEvent<ExchangeDetails>) {
    BaseScreen(
        screenTitle = stringResource(R.string.exchange_details_screen_title),
        isLoading = state.isLoading(),
        isError = state.isError()
    ) {
        state.getSuccessDataOrNull()?.let { data ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = data.exchangeId)
                Text(text = data.name ?: stringResource(R.string.exchange_no_name))
                Text(text = data.website)
                Text(text = data.volume1dayUsd.toDollarCurrency())
                Text(text = data.volume1hrsUsd.toDollarCurrency())
                Text(text = data.volume1mthUsd.toDollarCurrency())
            }
        }
    }
}