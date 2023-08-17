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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import dgsw.stac.knowledgender.remote.CardNewsDetailResponse
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import dgsw.stac.knowledgender.ui.feature.main.CardItem
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

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
fun ListItemView(item: CardItem,onRowItemClicked: (String) -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .width(200.dp)
            .clickable {
                onRowItemClicked(item.id)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
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

@Preview(showBackground = true)
@Composable
fun CardItemPreview() {
    KnowledgenderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            CardList(listOf()){

            }
        }
    }
}