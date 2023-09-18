package dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnewsdetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.components.NoNetworkChecking
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.BaseSky
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.dpToSp

@Composable
fun CardNewsDetailScreen(
    id: String,
    viewModel: CardNewsDetailViewModel = hiltViewModel(),
    backRequested: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val state by viewModel.cardNewsDetail.collectAsState()

    LaunchedEffect(true) {
        viewModel.getDetailInfo(id)
    }
    BackHandler(enabled = true) {
        backRequested.invoke()
    }

    Column(modifier = Modifier
        .padding(top = 56.dp)
        .fillMaxSize()) {
        state?.let { state ->
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                Banner(state.thumbnail)
                Spacer(modifier = Modifier.padding(top = 4.dp))
                Header(state.title, state.writer, state.category)
                Body(state.content, state.image)
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
//                    CircularProgressIndicator()
                CircularProgressIndicator()
            }
        }
//            Footer(
//                Modifier.padding(bottom = 68.dp),
//                onClickPrev = {  },
//                onClickNext = { },
//            )
    }

}

@Composable
private fun Banner(thumbnail: String) {
    Column {
        AsyncImage(
            model = thumbnail,
            contentDescription = "Thumbnail Image",
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()

        )
    }

}

@Composable
private fun Header(title: String, writer: String, category: String) {
    Column {
        BaseText(
            text = title,
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = dpToSp(24.dp)
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
                        fontSize = dpToSp(12.dp)
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
                        fontSize = dpToSp(12.dp)
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
private fun Body(content: String, image: String) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 24.dp)

    ) {
        Text(
            text = content,
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = dpToSp(16.dp),
                lineHeight = dpToSp(dp = 20.dp)
            )
        )
        SubcomposeAsyncImage(
            loading = { CircularProgressIndicator() },
            model = image,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentDescription = "image",
            contentScale = ContentScale.Crop
        )
    }
}

//@Composable
//private fun Footer(
//    modifier: Modifier = Modifier,
//    onClickPrev: () -> Unit,
//    onClickNext: () -> Unit,
//) {
//    Column(modifier = modifier) {
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Button(
//                onClick = onClickPrev,
//                Modifier
//                    .width(140.dp)
//                    .height(56.dp),
//                shape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = DarkestPurple
//                ),
//            ) {
//                Row(
//                    Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.knowledgedner_arrow_left),
//                        contentDescription = "Left Arrow",
//                        Modifier
//                            .height(12.dp)
//                            .width(8.dp)
//                    )
//                    Column(Modifier.padding(start = 16.dp)) {
//                        BaseText(
//                            text = "이전 글",
//                            color = Color.White,
//                            style = TextStyle(
//                                fontFamily = pretendard,
//                                fontWeight = FontWeight.Normal,
//                                fontSize = dpToSp(10.dp)
//                            )
//                        )
//                        BaseText(
//                            text = "글 제목",
//                            color = Color.White,
//                            style = TextStyle(
//                                fontFamily = pretendard,
//                                fontWeight = FontWeight.Bold,
//                                fontSize = dpToSp(12.dp)
//                            )
//                        )
//                    }
//                }
//            }
//            Button(
//                onClick = onClickNext,
//                Modifier
//                    .width(140.dp)
//                    .height(56.dp),
//                shape = RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = DarkestPurple
//                ),
//            ) {
//                Row(
//                    Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Column(Modifier.padding(end = 16.dp)) {
//                        BaseText(
//                            text = "다음 글",
//                            color = Color.White,
//                            style = TextStyle(
//                                fontFamily = pretendard,
//                                fontWeight = FontWeight.Normal,
//                                fontSize = dpToSp(10.dp)
//                            )
//                        )
//                        BaseText(
//                            text = "글 제목",
//                            color = Color.White,
//                            style = TextStyle(
//                                fontFamily = pretendard,
//                                fontWeight = FontWeight.Bold,
//                                fontSize = dpToSp(12.dp)
//                            )
//                        )
//                    }
//                    Image(
//                        painter = painterResource(id = R.drawable.knowledgender_arrow_right),
//                        contentDescription = "Riht Arrow",
//                        Modifier
//                            .height(12.dp)
//                            .width(8.dp)
//                    )
//                }
//            }
//        }
//    }
//}