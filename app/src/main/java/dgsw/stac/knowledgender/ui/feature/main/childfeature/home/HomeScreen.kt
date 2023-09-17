package dgsw.stac.knowledgender.ui.feature.main.childfeature.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.model.CardItem
import dgsw.stac.knowledgender.remote.BannerResponse
import dgsw.stac.knowledgender.ui.components.BannerView
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.components.CardLists
import dgsw.stac.knowledgender.ui.components.NoNetworkChecking
import dgsw.stac.knowledgender.ui.feature.main.CARDNEWS
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.LightSky
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.BODY
import dgsw.stac.knowledgender.util.CRIME
import dgsw.stac.knowledgender.util.EQUALITY
import dgsw.stac.knowledgender.util.HEART
import dgsw.stac.knowledgender.util.RELATIONSHIP
import dgsw.stac.knowledgender.util.dpToSp
import dgsw.stac.knowledgender.util.networkCheck

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onNavigationRequested: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = Unit) {
        viewModel.getBannerData()
    }
    val bannerData by viewModel.bannerData.collectAsState()
    if (networkCheck() || viewModel.cardNewsAvailable) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Header(bannerData)
            Body(viewModel, onNavigationRequested)
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
//                    CircularProgressIndicator()
            NoNetworkChecking()
        }
    }
}

data class IconData(val img: Int, val title: String, val category: String)
data class CardListData(val dataList: List<CardItem>, val topic: String, val des: String)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Header(bannerData: List<BannerResponse>?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        bannerData?.let {
          BannerView(it)
        } ?: run {
            Surface(modifier = Modifier.fillMaxSize(),color = LightSky) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    BaseText(
                        text = "준비된 배너가 없어요", color = DarkestBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Bold,
                            fontSize = dpToSp(24.dp)
                        )
                    )
                }
            }
        }

    }
}

@Composable
fun Body(
    viewModel: HomeViewModel,
    onNavigationRequested: (String) -> Unit
) {
    val iconData = listOf(
        IconData(R.drawable.heart, "마음", HEART),
        IconData(R.drawable.body, "신체", BODY),
        IconData(R.drawable.relationship, "관계", RELATIONSHIP),
        IconData(R.drawable.crime, "폭력", CRIME),
        IconData(R.drawable.equality, "평등", EQUALITY)
    )
    val cardData by produceState(initialValue = emptyList(), producer = {
        value = listOf(
            CardListData(
                viewModel.getCardCategory("마음"), "마음 상담소로 오세요", "내 안에 숨어있는 마음상담소로 초대합니다!"
            ), CardListData(
                viewModel.getCardCategory("신체"), "나만 몰랐던 나의 몸", "나조차도 모르고 있었던 나의 몸 속 비밀"
            ), CardListData(
                viewModel.getCardCategory("관계"),
                "너와 나의 연결고리",
                "즐겁고 행복한 우리의 관계를 건강하게 유지하는 법"
            ), CardListData(
                viewModel.getCardCategory("범죄"),
                "나를 확실하게 지키는 법",
                "폭력으로부터 나를 올바른 방법으로 보호해봅시다"
            ), CardListData(
                viewModel.getCardCategory("평등"), "세상에 같은 사람은 없다", "차이는 틀린 것이 아닌 다른 것!"
            )
        )
    })
    Icons(iconData, onNavigationRequested)
    CardLists(cardData, onNavigationRequested)
}

@Composable
fun Icon(data: IconData, onNavigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier.wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier.size(60.dp),
            onClick = {
                onNavigateTo("$CARDNEWS/${data.category}")
            }
        ) {
            Image(
                painter = painterResource(data.img),
                contentDescription = data.title,
                modifier = Modifier.size(30.dp)
            )
        }
        Text(
            text = data.title,
            style = TextStyle(
                fontSize = dpToSp(dp = 14.dp),
                color = BaseBlack,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
fun Icons(dataList: List<IconData>, onNavigateTo: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        dataList.forEach {
            Icon(it, onNavigateTo = onNavigateTo)
        }
    }
}
