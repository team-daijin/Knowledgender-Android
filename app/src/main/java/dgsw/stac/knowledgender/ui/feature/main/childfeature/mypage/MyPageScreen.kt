package dgsw.stac.knowledgender.ui.feature.main.childfeature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme

@Composable
fun MyPageScreen(modifier: Modifier = Modifier) {
    Column(modifier =  modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.fixing_img), contentDescription = "준비 안됨")
        Text(text = "아직 준비중입니다...")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    KnowledgenderTheme {
        MyPageScreen()
    }
}