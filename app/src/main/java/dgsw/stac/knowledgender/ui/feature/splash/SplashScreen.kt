package dgsw.stac.knowledgender.ui.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.navigation.Route.HOME
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigationRequsted: (String) -> Unit) {

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        navigationRequsted(HOME)
    }
    Column {
        Box(
            modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.align(Alignment.Center).size(100.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "알고싶었성"
            )
        }
    }
}