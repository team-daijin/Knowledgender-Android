package dgsw.stac.knowledgender.ui.feature.chatting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.components.BaseTextField
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.LightSky
import dgsw.stac.knowledgender.ui.theme.pretendard

@Composable
fun ChattingScreen(viewModel: ChattingViewModel) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val messageState = viewModel.message.collectAsState()

        Body(modifier = Modifier.weight(1f))
        Footer(messageState, viewModel = viewModel)
    }
}

@Composable
private fun Body(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = LightSky
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {

        }
    }
}

@Composable
private fun Footer(message: State<String>, viewModel: ChattingViewModel) {
    Surface(
        modifier = Modifier
            .heightIn(56.dp)
            .fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp),
        border = BorderStroke(
            1.dp,
            LightSky
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            BaseTextField(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(0.8f),
                value = message.value,
                placeHolder = "메시지를 입력해보세요.",
                onValueChange = { viewModel.message.value = it }
            )
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(44.dp)
                    .widthIn(60.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BasePurple),
                onClick = { },
                contentPadding = PaddingValues(1.dp)
            ) {
                BaseText(
                    text = "전송",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    KnowledgenderTheme {
        ChattingScreen(hiltViewModel())
    }
}