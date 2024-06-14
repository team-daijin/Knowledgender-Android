import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dgsw.stac.knowledgender.navigation.Route.CARDNEWSDETAIL
import dgsw.stac.knowledgender.remote.CardResponse
import dgsw.stac.knowledgender.ui.feature.main.CARDNEWSDETAIL
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.dpToSp
import site.algosipeosseong.model.CardnewsCategory
import site.algosipeosseong.model.Cards

@Composable
fun CardNewsItemView(
    modifier: Modifier = Modifier,
    category: String,
    item: Cards,
    onNavigationRequested: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = 8.dp)
            .width(165.dp)
            .clickable { onNavigationRequested(CARDNEWSDETAIL + "/" + item.id) }
    ) {
        Box(modifier = Modifier.clip(RoundedCornerShape(topStart = 10.dp))) {
            AsyncImage(
                model = item.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 4.dp)
                    .size(width = 45.dp, height = 20.dp)
                    .background(
                        Color.White.copy(alpha = 0.6f)
                    )
                    .align(Alignment.BottomStart)

                    .border(width = 0.6.dp, color = BasePurple),
                contentAlignment = Alignment.Center
            ) {
                BaseText(
                    text = category, color = DarkestPurple, style = TextStyle(
                        fontFamily = pretendard, fontSize = dpToSp(
                            dp = 10.dp
                        ), fontWeight = FontWeight.Medium
                    )
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.title,
            color = DarkestBlack,
            style = TextStyle(fontFamily = pretendard, fontWeight = FontWeight.Medium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = dpToSp(dp = 16.dp),
            textAlign = TextAlign.Left
        )
        Text(
            text = item.subTitle,
            color = LightBlack,
            style = TextStyle(fontFamily = pretendard, fontWeight = FontWeight.Normal),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = dpToSp(dp = 12.dp),
            textAlign = TextAlign.Left
        )
    }
}

@Composable
fun CardnewsCategoryView(
    modifier: Modifier = Modifier,
    item: CardnewsCategory,
    onNavigationRequested: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .clip(RoundedCornerShape(topStart = 20.dp))
    ) {
        AsyncImage(
            model = item.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 187.dp)
                .clickable { onNavigationRequested(CARDNEWSDETAIL + "/" + item.id) },
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 4.dp)
                .size(width = 70.dp, height = 33.dp)
                .background(
                    Color.White.copy(alpha = 0.6f)
                )
                .align(Alignment.BottomStart)

                .border(width = 0.6.dp, color = BasePurple),
            contentAlignment = Alignment.Center
        ) {
            BaseText(
                text = item.category, color = DarkestPurple, style = TextStyle(
                    fontFamily = pretendard, fontSize = dpToSp(
                        dp = 16.dp
                    ), fontWeight = FontWeight.Medium
                )
            )
        }
    }


    Spacer(modifier = Modifier.height(8.dp))
    Column(modifier = Modifier.padding(start = 20.dp)) {
        Text(
            text = item.title,
            color = DarkestBlack,
            style = TextStyle(fontFamily = pretendard, fontWeight = FontWeight.Medium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = dpToSp(dp = 20.dp),
            textAlign = TextAlign.Left
        )
        Text(
            text = item.subTitle,
            color = LightBlack,
            style = TextStyle(fontFamily = pretendard, fontWeight = FontWeight.Normal),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = dpToSp(dp = 16.dp),
            textAlign = TextAlign.Left
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
}
