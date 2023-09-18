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

@OptIn(ExperimentalPagerApi::class)
@Composable
@ExperimentalFoundationApi
fun BannerView(item: List<BannerResponse>) {
    val pagerState = rememberPagerState(initialPage = 0)


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