package dgsw.stac.knowledgender.ui.feature.mypage

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme

@Composable
fun MyPageScreen(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    KnowledgenderTheme {
        MyPageScreen("Android")
    }
}