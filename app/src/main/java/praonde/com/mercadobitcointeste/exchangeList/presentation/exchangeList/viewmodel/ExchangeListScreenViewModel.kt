package praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import praonde.com.mercadobitcointeste.common.LoadingEvent
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeData
import praonde.com.mercadobitcointeste.exchangeList.domain.repository.ExchangeRepository

class ExchangeListScreenViewModel(repository: ExchangeRepository) : ViewModel() {

    val state = repository.getExchangeList()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LoadingEvent.Loading)
}