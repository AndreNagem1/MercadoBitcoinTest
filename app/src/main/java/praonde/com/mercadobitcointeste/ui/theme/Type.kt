package praonde.com.mercadobitcointeste.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Immutable

@Immutable
data class AppTypography(
    val labelBold: TextStyle,
    val displayNormal: TextStyle,
)

internal val BsTypographyConst = AppTypography(
    labelBold = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    displayNormal = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
)