package praonde.com.mercadobitcointeste.exchangeList.data.remote

import praonde.com.mercadobitcointeste.exchangeList.data.entity.ExchangeListEntityItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeListApi {

    @GET("exchanges")
    suspend fun getExchangeList(): Response<List<ExchangeListEntityItem>>

    @GET("exchanges/{exchange_id}")
    suspend fun getExchangeDetails(
        @Path("exchange_id") exchangeId: String,
    ):  Response<List<ExchangeListEntityItem>>

}