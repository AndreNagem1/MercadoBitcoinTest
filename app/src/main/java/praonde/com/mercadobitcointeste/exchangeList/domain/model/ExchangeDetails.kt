package praonde.com.mercadobitcointeste.exchangeList.domain.model

data class ExchangeDetails(
    val exchangeId: String,
    val name: String?,
    val rank: Int,
    val volume1dayUsd: Double,
    val volume1hrsUsd: Double,
    val volume1mthUsd: Double,
    val website: String
)

