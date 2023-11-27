package dgsw.stac.knowledgender.ui.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dgsw.stac.knowledgender.remote.BannerResponse
import dgsw.stac.knowledgender.remote.CardResponse
import dgsw.stac.knowledgender.ui.components.BannerView
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.components.CardNewsItemView
import dgsw.stac.knowledgender.ui.components.CategoryIcon
import dgsw.stac.knowledgender.ui.components.HomeTitleText
import dgsw.stac.knowledgender.ui.components.NoNetworkChecking
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
import dgsw.stac.knowledgender.util.safeLet

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    positionChecked: MutableState<Boolean>,
    onNavigationRequested: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    val bannerData by viewModel.bannerData.collectAsState()
    val heart by viewModel.heart.collectAsState()
    val body by viewModel.body.collectAsState()
    val relationship by viewModel.relationship.collectAsState()
    val crime by viewModel.crime.collectAsState()
    val equality by viewModel.equality.collectAsState()
    var visible by remember { mutableStateOf(false) }

    safeLet(
        heart,
        body,
        relationship,
        crime,
        equality
    ) { checkedHeart, checkedBody, checkedRelationship, checkedCrime, checkedEquality ->
        if (networkCheck() || viewModel.cardNewsAvailable.value) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Header(bannerData)
                Spacer(modifier = Modifier.height(32.dp))
                Body(
                    heart = checkedHeart,
                    body = checkedBody,
                    relationship = checkedRelationship,
                    crime = checkedCrime,
                    equality = checkedEquality,
                    onNavigationRequested = onNavigationRequested

                ) {
                    positionChecked.value = scrollState.value >= it.value
                }
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
    } ?: run {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Header(bannerData: List<BannerResponse>?) {


    bannerData?.let {
        BannerView(it)
    } ?: run {
        Surface(modifier = Modifier.fillMaxSize(), color = LightSky) {
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

@Composable
fun Body(
    heart: List<CardResponse>,
    body: List<CardResponse>,
    relationship: List<CardResponse>,
    crime: List<CardResponse>,
    equality: List<CardResponse>,
    onNavigationRequested: (String) -> Unit,
    position: (Dp) -> Unit
) {

    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                columnHeightDp = it.positionInParent().y.dp - 175.dp
                position(columnHeightDp)
            }, horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CategoryIcon(category = HEART, onNavigationRequested)
        CategoryIcon(category = BODY, onNavigationRequested)
        CategoryIcon(category = RELATIONSHIP, onNavigationRequested)
        CategoryIcon(category = CRIME, onNavigationRequested)
        CategoryIcon(category = EQUALITY, onNavigationRequested)
    }
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        HomeTitleText(text = "마음 상담소로 오세요", subText = "내 안에 숨어있는 마음상담소로 초대합니다!")
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(start = 16.dp)
        ) {
            items(heart) {
                CardNewsItemView(Modifier,it, onNavigationRequested)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        HomeTitleText(text = "나만 몰랐던 나의 몸", subText = "나조차도 모르고 있었던 나의 몸 속 비밀")
        LazyRow(modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(start = 16.dp)) {
            items(body) {
                CardNewsItemView(Modifier,it, onNavigationRequested)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        HomeTitleText(text = "너와 나의 연결고리", subText = "즐겁고 행복한 우리의 관계를 건강하게 유지하는 법")
        LazyRow(modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(start = 16.dp)) {
            items(relationship) {
                CardNewsItemView(Modifier,it, onNavigationRequested)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        HomeTitleText(text = "나를 확실하게 지키는 법", subText = "폭력으로부터 나를 올바른 방법으로 보호해봅시다")
        LazyRow(modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(start = 16.dp)) {
            items(crime) {
                CardNewsItemView(Modifier,it, onNavigationRequested)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        HomeTitleText(text = "세상에 같은 사람은 없다", subText = "차이는 틀린 것이 아닌 다른 것!")
        LazyRow(modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(start = 16.dp)) {
            items(equality) {
                CardNewsItemView(Modifier,it, onNavigationRequested)
            }
        }
    }

}

