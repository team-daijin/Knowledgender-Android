package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.remote.BannerResponse
import dgsw.stac.knowledgender.ui.theme.LightSky
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val AUTO_PAGE_CHANGE_DELAY = 5000L
@OptIn(ExperimentalPagerApi::class)
@Composable
@ExperimentalFoundationApi
fun BannerView(item: List<BannerResponse>) {
    val pagerState = rememberPagerState(initialPage = 0)

    // 지정한 시간마다 auto scroll.
    // 유저의 스크롤해서 페이지가 바뀐경우 다시 실행시키고 싶기 때문에 key는 currentPage.
    LaunchedEffect(key1 = pagerState.currentPage) {
        launch {
            while (true) {
                delay(AUTO_PAGE_CHANGE_DELAY)
                // 페이지 바뀌었다고 애니메이션이 멈추면 어색하니 NonCancellable
                withContext(NonCancellable) {
                    // 일어날린 없지만 유저가 약 10억번 스크롤할지 몰라.. 하는 사람을 위해..
                    if (pagerState.currentPage + 1 in 0..Int.MAX_VALUE) {
                        pagerState.animateScrollToPage((pagerState.currentPage + 1)%item.size)
                    }
                }
            }
        }
    }
//    val pageCount = 1
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        Alignment.BottomCenter
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxHeight(),
            count = item.size,
            state = pagerState,
            userScrollEnabled = true,
            content = {page ->
                AsyncImage(
                    model = item[page].fileUrl,
                    contentDescription = "Banner",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        )
        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = item.size,
            activeColor = Color.White,
            inactiveColor = LightSky,
            spacing = 10.dp,
            indicatorWidth = 5.dp,
            indicatorHeight = 5.dp,
            modifier = Modifier.padding(15.dp)
        )
    }
}