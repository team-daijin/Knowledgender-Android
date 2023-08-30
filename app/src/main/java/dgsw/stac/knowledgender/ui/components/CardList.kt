package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import dgsw.stac.knowledgender.model.CardItem
import dgsw.stac.knowledgender.ui.feature.main.CARDNEWSDETAIL
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.CardListData
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.LighterBlack
import dgsw.stac.knowledgender.ui.theme.pretendard

@Composable
fun Card(data: CardListData, onRowItemClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        BaseText(
            modifier = Modifier.padding(5.dp),
            text = data.topic,
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        )
        BaseText(
            modifier = Modifier.padding(5.dp),
            text = data.des,
            color = LighterBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
        )
        Spacer(modifier = Modifier.width(120.dp))
        CardList(data.dataList, onRowItemClicked)
    }
}

@Composable
fun CardLists(data: List<CardListData>, onNavigationRequested: (String) -> Unit) {
    data.forEach {
        Card(it, onNavigationRequested)
    }
}

@Composable
fun CardList(dataList: List<CardItem>, onRowItemClicked: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxSize()
    ) {
        items(dataList) { item ->
            ListItemView(item, onRowItemClicked = onRowItemClicked)
        }
    }
}


@Composable
fun ListItemView(item: CardItem, onRowItemClicked: (String) -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .width(200.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    onRowItemClicked(CARDNEWSDETAIL + "/" + item.id)
                }
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = item.image),
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(20.dp))
            BaseText(
                text = item.title,
                color = DarkestBlack,
                style = TextStyle(
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = DarkestPurple,
                    shape = CircleShape
                )
            ) {
                BaseText(
                    modifier = Modifier.padding(25.dp, 5.dp, 25.dp, 5.dp),
                    text = item.category,
                    color = DarkestPurple,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardItemPreview() {
    KnowledgenderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            CardList(listOf()) {

            }
        }
    }
}