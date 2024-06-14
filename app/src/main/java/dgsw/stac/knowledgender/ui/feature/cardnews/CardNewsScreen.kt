

package dgsw.stac.knowledgender.ui.feature.cardnews

import CardNewsItemView
import CardnewsCategoryView
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dgsw.stac.knowledgender.remote.CardResponse
import dgsw.stac.knowledgender.remote.CardResponseList
import dgsw.stac.knowledgender.ui.theme.DarkGradient
import dgsw.stac.knowledgender.ui.theme.LightGradient
import androidx.navigation.NavDestination
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.navigation.Route
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.Category
import dgsw.stac.knowledgender.util.dpToSp
import site.algosipeosseong.model.CardnewsCategory


@Composable
fun CardNewsScreen(
    currentRoute: NavDestination?,
    category: Category,
    viewModel: CardNewsViewModel,
    onNavigtionRequested: (String) -> Unit,
    backRequested: () -> Unit
) {

    val scrollState = rememberScrollState()

    val state by viewModel.cardNews.collectAsState()

    Log.d("euya", currentRoute?.route.toString())

    LaunchedEffect(key1 = Unit) {
        viewModel.getCardNewsByCategory(category)
    }
    BackHandler(enabled = true) {
        backRequested.invoke()
    }

    val categoryItem = listOf(
        CategoryItem.Heart,
        CategoryItem.Body,
        CategoryItem.Relation,
        CategoryItem.Violence,
        CategoryItem.Equality
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        state?.let { state ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                categoryItem.forEach {
                    Header(
                        category = category,
                        item = it,
                        onNavigtionRequested = onNavigtionRequested
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Body(
                title = viewModel.getTitle(category = category.str),
                des = viewModel.getSubTitle(category = category.str),
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
                CircularProgressIndicator()
            }
        }
    }
}

enum class CategoryItem(val route: String, val drawable: Int, val text: String) {
    Heart(route = Category.HEART.str, drawable = R.drawable.category_heart, text = "마음"),
    Body(route = Category.BODY.str, drawable = R.drawable.category_body, text = "신체"),
    Relation(
        route = Category.RELATION.str,
        drawable = R.drawable.category_relationship,
        text = "관계"
    ),
    Violence(route = Category.VIOLENCE.str, drawable = R.drawable.category_violence, text = "범죄"),
    Equality(route = Category.EQUALITY.str, drawable = R.drawable.category_equality, text = "평등")
}

@Composable
private fun Header(
    category: Category,
    item: CategoryItem,
    onNavigtionRequested: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 60.dp, height = 30.dp)
            .border(
                width = 1.dp,
                color = if (item.route == category.str) BasePurple else LightBlack,
                shape = RoundedCornerShape(50.dp)
            )
            .clickable {
                if (item.route != category.str) {
                    onNavigtionRequested("${Route.CARDNEWS}/${item.route}")
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = item.drawable),
                tint = if (item.route == category.str) BasePurple else LightBlack,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 2.dp),
                color = if (item.route == category.str) BasePurple else LightBlack,
                text = item.text, style = TextStyle(
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = dpToSp(dp = 12.dp)
                )
            )
        }
    }
}

@Composable
private fun Body(
    title: String,
    des: String,
    modifier: Modifier = Modifier,
    cardNewsInfo: List<CardnewsCategory>,
    onNavigationRequested: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.category_banner),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(start = 28.dp), style = TextStyle(
                    fontSize = dpToSp(18.dp),
                    color = Color.White,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = des,
                modifier = Modifier.padding(top = 4.dp, start = 28.dp), style = TextStyle(
                    fontSize = dpToSp(12.dp),
                    color = Color.White,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }

    Column(modifier = modifier) {
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
    dataList: List<CardnewsCategory>,
    onNavigtionRequested: (String) -> Unit
) {
    LazyColumn {
        items(dataList) {
            CardnewsCategoryView(item = it, onNavigationRequested = onNavigtionRequested)
        }
    }
}




