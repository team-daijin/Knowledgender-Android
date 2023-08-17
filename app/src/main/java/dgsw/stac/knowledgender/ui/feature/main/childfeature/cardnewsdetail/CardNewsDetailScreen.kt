package dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnewsdetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.BaseSky
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.Utility.categoryToString
import java.net.URL

@Composable
fun CardNewsDetailScreen(
    id: String,
    viewModel: CardNewsDetailViewModel = hiltViewModel(),
    backRequested: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigationRequested: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val state by viewModel.cardNewsDetail.collectAsState()

    LaunchedEffect(true) {
        viewModel.getDetailInfo(id)
    }
    BackHandler(enabled = true) {
        backRequested.invoke()
    }
    
    Column {
        Banner()
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(scrollState)
            ) {
                Header(state.title,state.writer,categoryToString(state.category))
                Body(state.content,URL(state.image))
            }
            Footer(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 68.dp),
                onClickPrev = { },
                onClickNext = { },
            )
        }

    }
}

@Composable
private fun Banner() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.android_sample),
            contentDescription = "Column",
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()

        )
    }

}

@Composable
private fun Header(title: String, writer: String,category: String) {

    Column {
        BaseText(
            text = title,
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Row(
            Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.profile_ex),
                    contentDescription = "logo",
                    modifier = Modifier.height(24.dp)
                )
                BaseText(
                    Modifier.padding(start = 8.dp),
                    text = writer,
                    color = LightBlack,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                )
            }
            Box(
                Modifier
                    .border(1.dp, BasePurple, RoundedCornerShape(10.dp))
                    .height(22.dp)
                    .width(61.dp),
                Alignment.Center,

                ) {
                BaseText(
                    text = category,
                    color = BasePurple,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(1.dp)
                .background(BaseSky)
        )
    }

}

@Composable
private fun Body(content: String,image:URL) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 24.dp)

    ) {
        BaseText(
            text = content,
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.android_sample),
            contentDescription = "으야",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    onClickPrev: () -> Unit,
    onClickNext: () -> Unit,
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = onClickPrev,
                Modifier
                    .width(140.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkestPurple
                ),
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.knowledgedner_arrow_left),
                        contentDescription = "Left Arrow",
                        Modifier
                            .height(12.dp)
                            .width(8.dp)
                    )
                    Column(Modifier.padding(start = 16.dp)) {
                        BaseText(
                            text = "이전 글",
                            color = Color.White,
                            style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp
                            )
                        )
                        BaseText(
                            text = "글 제목",
                            color = Color.White,
                            style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
            Button(
                onClick = onClickNext,
                Modifier
                    .width(140.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkestPurple
                ),
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.padding(end = 16.dp)) {
                        BaseText(
                            text = "다음 글",
                            color = Color.White,
                            style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp
                            )
                        )
                        BaseText(
                            text = "글 제목",
                            color = Color.White,
                            style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.knowledgender_arrow_right),
                        contentDescription = "Riht Arrow",
                        Modifier
                            .height(12.dp)
                            .width(8.dp)
                    )
                }
            }
        }
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
            CardNewsDetailScreen("", hiltViewModel(),{}){

            }
        }
    }
}