

package dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnews

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dgsw.stac.knowledgender.remote.CardCategoryResponse
import dgsw.stac.knowledgender.ui.feature.main.CARDNEWSDETAIL
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkGradient
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.LightGradient


@Composable
fun CardNewsScreen(
    category: String,
    viewModel: CardNewsViewModel,
    onNavigtionRequested: (String) -> Unit,
    backRequested: () -> Unit
) {

    val scrollState = rememberScrollState()

    val state by viewModel.cardNews.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getCardNewsByCategory(category)
    }
    BackHandler(enabled = true) {
        backRequested.invoke()
    }

    Column(
        modifier = Modifier
            .padding(top = 48.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        state?.let { state ->
            Body(
                title = viewModel.getTitle(category = category),
                des = viewModel.getDescription(category = category),
                modifier = Modifier.weight(1f),
                cardNewsInfo = state,
                onNavigationRequested = onNavigtionRequested
            )
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
    }
}


@Composable
fun Body(
    title: String,
    des: String,
    modifier: Modifier = Modifier, cardNewsInfo: List<CardCategoryResponse>,
    onNavigationRequested: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(brush = Brush.verticalGradient(listOf(LightGradient, DarkGradient)))
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(top = 28.dp, start = 28.dp), style = TextStyle(
                fontSize = 24.sp,
                color = Color.White,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = des,
            modifier = Modifier.padding(top = 4.dp, start = 28.dp), style = TextStyle(
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal
            )
        )
    }
    Column(
        modifier = modifier
            .padding(10.dp)
    ) {

        Spacer(modifier = Modifier.height(24.dp))
        CardNews(
            modifier = Modifier.weight(1f),
            dataList = cardNewsInfo,
            onNavigtionRequested = onNavigationRequested
        )
    }
}

@Composable
fun CardNews(
    modifier: Modifier = Modifier,
    dataList: List<CardCategoryResponse>,
    onNavigtionRequested: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.padding(16.dp),
        columns = GridCells.Adaptive(128.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
    ) {
        items(dataList) { item ->
            CardNewsItemView(item, onNavigtionRequested)
        }
    }

//    LazyColumn(modifier = modifier) {
//        items(dataList) {
//            CardNewsItemView(it)
//        }
//    }

//    Column {
//        dataList.getOrNull(0)?.let {
//            CardNewsItemView(it)
//        }
//    }
}



@Composable
fun CardNewsItemView(item: CardCategoryResponse, onNavigationRequested: (String) -> Unit) {
    Column(modifier = Modifier.clickable { onNavigationRequested(CARDNEWSDETAIL + "/" + item.id) }
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .width(165.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = item.title,
            color = BasePurple,
            fontSize = 20.sp,
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier.border(
                width = 1.dp,
                color = DarkestPurple,
                shape = CircleShape
            )
        ) {
            Text(
                modifier = Modifier.padding(25.dp, 5.dp, 25.dp, 5.dp),
                text = item.category,
                style = TextStyle(
                    fontSize = 15.sp,
                    color = DarkestPurple,
                    textAlign = TextAlign.Left
                )
            )
        }
    }
}
