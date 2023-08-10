package dgsw.stac.knowledgender.ui.feature.cardnewsdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme


@Composable
fun CardNewsDetailScreen(modifier: Modifier = Modifier) {
    Column {
        Header()
        Body()
        Footer()
    }
}

@Composable
private fun Header() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.android_sample),
            contentDescription = "Column",
            modifier = Modifier.height(180.dp).fillMaxWidth()

        )
    }

}

@Composable
private fun Body() {
    Column {
        val euya = 1

    }
}

@Composable
private fun Footer() {
    Column {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    KnowledgenderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CardNewsDetailScreen()
        }

    }
}