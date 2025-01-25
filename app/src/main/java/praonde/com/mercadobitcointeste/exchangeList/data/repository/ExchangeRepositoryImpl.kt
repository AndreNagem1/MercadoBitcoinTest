package praonde.com.mercadobitcointeste.exchangeList.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import praonde.com.mercadobitcointeste.common.LoadingEvent
import praonde.com.mercadobitcointeste.common.mapSuccess
import praonde.com.mercadobitcointeste.exchangeList.data.remote.ExchangeDataStore
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeData
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeDetails
import praonde.com.mercadobitcointeste.exchangeList.domain.repository.ExchangeRepository

class ExchangeRepositoryImpl(private val dataStore: ExchangeDataStore) :
    ExchangeRepository {

    override fun getExchangeList(): Flow<LoadingEvent<List<ExchangeData>>> {
        return dataStore.getExchangeList().mapSuccess { responseList ->
            val listExchanges = mutableListOf<ExchangeData>()

            responseList.forEach { data ->
                val exchangeData = ExchangeData(
                    id = data.exchangeId,
                    name = data.name,
                    volumePerDayUsd = data.volume1dayUsd
                )

                listExchanges.add(exchangeData)
            }

            listExchanges
        }
    }

    override fun getExchangeDetails(exchangeID: String): Flow<LoadingEvent<ExchangeDetails>> {
        return dataStore.getExchangeDetails(exchangeId = exchangeID).mapSuccess { data ->
            ExchangeDetails(
                exchangeId = data[0].exchangeId,
                name = data[0].name,
                rank = data[0].rank,
                volume1dayUsd = data[0].volume1dayUsd,
                volume1hrsUsd = data[0].volume1hrsUsd,
                volume1mthUsd = data[0].volume1mthUsd,
                website = data[0].website
            )
        }
    }
}