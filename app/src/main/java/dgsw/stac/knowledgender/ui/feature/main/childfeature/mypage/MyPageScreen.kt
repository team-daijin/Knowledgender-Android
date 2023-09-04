package dgsw.stac.knowledgender.ui.feature.main.childfeature.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.pretendard

@Composable
fun MyPageScreen(modifier: Modifier = Modifier, viewModel: MyPageViewModel) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getProfile()
    }

    val state by viewModel.profile.collectAsState()

    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BaseText(
            text = state.name,
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )
        BaseText(
            text = "${state.age} | ${state.gender}",
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )
        
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    KnowledgenderTheme {
        MyPageScreen(viewModel = hiltViewModel())
    }
}