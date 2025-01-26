package praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import praonde.com.mercadobitcointeste.common.network.LoadingEvent
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeData
import praonde.com.mercadobitcointeste.exchangeList.domain.repository.ExchangeRepository

class ExchangeListScreenViewModel(private val repository: ExchangeRepository) : ViewModel() {

    private val _exchangeList =
        MutableStateFlow<LoadingEvent<List<ExchangeData>>>(LoadingEvent.Loading)

    val state = _exchangeList
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LoadingEvent.Loading)

    private fun getExchangeList() {
        viewModelScope.launch {
            repository.getExchangeList().collect {
                _exchangeList.value = it
            }
        }
    }

    init {
        getExchangeList()
    }
}