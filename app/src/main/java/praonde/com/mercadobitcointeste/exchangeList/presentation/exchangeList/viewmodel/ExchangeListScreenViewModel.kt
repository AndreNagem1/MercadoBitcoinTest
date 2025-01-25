package praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import praonde.com.mercadobitcointeste.common.LoadingEvent
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeData
import praonde.com.mercadobitcointeste.exchangeList.domain.repository.ExchangeRepository

class ExchangeListScreenViewModel(private val repository: ExchangeRepository) : ViewModel() {

    private val _exchangeList =
        MutableStateFlow<LoadingEvent<List<ExchangeData>>>(LoadingEvent.Loading)

    val state: StateFlow<LoadingEvent<List<ExchangeData>>> = _exchangeList

    init {
        getExchangeList()
    }

    private fun getExchangeList() {
        viewModelScope.launch {
            repository.getExchangeList().collect {
                _exchangeList.value = it
            }
        }
    }
}