package dgsw.stac.knowledgender.ui.feature.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import dgsw.stac.knowledgender.remote.Category
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestPurple

@Composable
fun CardList(dataList: List<CardItem>) {
    LazyRow(
        modifier = Modifier.fillMaxSize()
    ) {
        items(dataList) { item ->
            ListItemView(item)
        }
    }
}

@Composable
fun ListItemView(item: CardItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .width(200.dp)
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