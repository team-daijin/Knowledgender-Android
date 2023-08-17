package dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnews

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import dgsw.stac.knowledgender.remote.Category
import dgsw.stac.knowledgender.ui.feature.main.CardItem
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.CardListData
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme


@Composable
fun CardNewsScreen(cartegory: String,viewModel: CardNewsViewModel, onNavigateRequested: (String) -> Unit) {

    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = Unit){
        viewModel.getCardNewsByCategory(cartegory)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
//        Header()
//        Body(viewModel)
    }
}


@Composable
fun Header() {
}

@Composable
fun Body(data: CardListData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(BasePurple)
        ) {
            Text(
                text = data.topic, modifier = Modifier.padding(5.dp), style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = data.des, modifier = Modifier.padding(5.dp), style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        Spacer(modifier = Modifier.width(40.dp))
        CardNews(data.dataList)
    }
}

@Composable
fun CardNews(dataList: List<CardItem>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(dataList) { item ->
            CardNewsItemView(item)
        }
    }
}

@Composable
fun CardNewsItemView(item: CardItem) {
    Surface(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(0.5f)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.image),
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
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
                    text = item.category.toString(),
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = DarkestPurple,
                        textAlign = TextAlign.Left
                    )
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CardNewsPreview() {
    KnowledgenderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            CardNewsScreen("",hiltViewModel()){

            }
        }
    }
}
