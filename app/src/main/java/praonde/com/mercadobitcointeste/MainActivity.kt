package praonde.com.mercadobitcointeste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeDetails.ui.ExchangeDetailsScreen
import praonde.com.mercadobitcointeste.exchangeList.presentation.exchangeList.ui.ExchangeListScreen
import praonde.com.mercadobitcointeste.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ExchangeListScreen
                ) {
                    composable<ExchangeListScreen> {
                        ExchangeListScreen {
                            navController.navigate(DetailsScreen(exchangeId = it))
                        }
                    }
                    composable<DetailsScreen> {
                        val args = it.toRoute<DetailsScreen>()

                        ExchangeDetailsScreen(
                            exchangeId = args.exchangeId
                        )
                    }
                }
            }
        }
    }
}

@Serializable
object ExchangeListScreen

@Serializable
data class DetailsScreen(val exchangeId: String)