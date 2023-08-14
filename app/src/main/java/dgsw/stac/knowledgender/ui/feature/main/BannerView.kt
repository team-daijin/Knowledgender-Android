package dgsw.stac.knowledgender.ui.feature.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import dgsw.stac.knowledgender.remote.BannerResponse


@OptIn(ExperimentalPagerApi::class)
@Composable
@ExperimentalFoundationApi
fun BannerView(item: List<BannerResponse>) {
    val pagerState = rememberPagerState(initialPage = 0)
    val pageCount = item.size
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        Alignment.BottomCenter
    ) {
        HorizontalPager(
            count = pageCount,
            state = pagerState,
            userScrollEnabled = true,
            content = { page ->
                Image(
                    painter = rememberAsyncImagePainter(item[page].banner),
                    contentDescription = "Banner",
                    modifier = Modifier
                        .height(320.dp)
                        .fillMaxWidth()
//                        .clickable {}
                )
            })
        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = pageCount,
            activeColor = Color.White,
            inactiveColor = Color.Gray,
            spacing = 10.dp,
            indicatorWidth = 5.dp,
            indicatorHeight = 5.dp,
            modifier = Modifier.padding(15.dp)
        )
    }
}