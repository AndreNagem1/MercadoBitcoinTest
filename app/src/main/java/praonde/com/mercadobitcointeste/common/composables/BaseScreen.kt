package praonde.com.mercadobitcointeste.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun BaseScreen(
    screenTitle: String,
    isLoading: Boolean = false,
    isError: Boolean = false,
    content: @Composable () -> Unit
) {
    Column {
        TopBar(screenTitle = screenTitle)
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
                .fillMaxWidth()
        ) {
            content()

            if (isLoading) {
                Column(
                    modifier = Modifier
                        .testTag("LoadingScreen")
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.Blue)
                }
            }

            if (isError) {
                Column(
                    modifier = Modifier
                        .testTag("ErrorScreen")
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Something went wrong :(")
                }
            }
        }
    }
}

@Composable
fun TopBar(screenTitle: String) {
    Row(
        modifier = Modifier
            .background(Color.Blue)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = screenTitle,
            modifier = Modifier.padding(top = 30.dp, bottom = 15.dp),
            color = Color.White
        )
    }
}