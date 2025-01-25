package praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import praonde.com.mercadobitcointeste.common.extensions.toDollarCurrency
import praonde.com.mercadobitcointeste.exchangeList.domain.model.ExchangeData

@Composable
fun ExchangeCard(data: ExchangeData, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White
        ),
        border = BorderStroke(width = 2.dp, color = Color.Gray),
        onClick = { onCardClick() }
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            ExchangeCardItem(
                label = "Exchange ID:",
                value = data.id
            )
            ExchangeCardItem(
                label = "Name:",
                value = data.name ?: "Sem nome"
            )
            ExchangeCardItem(
                label = "Volume per day:",
                value = data.volumePerDayUsd.toDollarCurrency()
            )
        }
    }
}

@Composable
fun ExchangeCardItem(label: String, value: String) {
    Spacer(modifier = Modifier.height(4.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
            color = Color.Black
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = value,
            color = Color.Black
        )
    }
}