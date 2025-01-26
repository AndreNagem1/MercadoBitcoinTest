package praonde.com.mercadobitcointeste.exchangeList.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import praonde.com.mercadobitcointeste.common.network.LoadingEvent
import praonde.com.mercadobitcointeste.exchangeList.data.entity.ExchangeListEntityItem

class ExchangeDataStore(private val exchangeListApi: ExchangeListApi) {

    fun getExchangeList(): Flow<LoadingEvent<List<ExchangeListEntityItem>>> {
        return flow {
            emit(LoadingEvent.Loading)
            try {
                val response = exchangeListApi.getExchangeList()
                if (response.isSuccessful) {
                    response.body()?.let { emit(LoadingEvent.Success(it)) }
                } else {
                    val error =
                        Exception("Error ExchangeListDataStore: ${response.code()} ${response.message()}")
                    emit(LoadingEvent.Error(error))
                }
            } catch (e: Exception) {
                val error = Exception("Error ExchangeListDataStore")
                emit(LoadingEvent.Error(error))
            }

        }.flowOn(Dispatchers.IO)
    }

    fun getExchangeDetails(exchangeId: String): Flow<LoadingEvent<List<ExchangeListEntityItem>>> {
        return flow {
            emit(LoadingEvent.Loading)
            try {
                val response = exchangeListApi.getExchangeDetails(
                    exchangeId = exchangeId
                )
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(LoadingEvent.Success(it))
                    }
                } else {
                    val error =
                        Exception("Error ExchangeListDataStore: ${response.code()} ${response.message()}")
                    emit(LoadingEvent.Error(error))
                }
            } catch (e: Exception) {
                val error = Exception("Error ExchangeListDataStore")
                emit(LoadingEvent.Error(error))
            }

        }.flowOn(Dispatchers.IO)
    }
}