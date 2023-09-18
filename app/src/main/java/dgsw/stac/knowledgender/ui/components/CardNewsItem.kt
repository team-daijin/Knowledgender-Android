package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dgsw.stac.knowledgender.remote.CardResponse
import dgsw.stac.knowledgender.ui.feature.main.CARDNEWSDETAIL
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.util.dpToSp

@Composable
fun CardNewsItemView(item: CardResponse, onNavigationRequested: (String) -> Unit) {
    Column(modifier = Modifier.width(165.dp)
        .padding(vertical = 10.dp, horizontal = 8.dp)
        .clickable { onNavigationRequested(CARDNEWSDETAIL + "/" + item.id) }
    ) {
        Surface(shape = RoundedCornerShape(8.dp)) {
            AsyncImage(
                model = item.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.title,
            color = DarkestBlack,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = dpToSp(dp = 16.dp),
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier.border(
                width = 1.dp,
                color = BasePurple,
                shape = CircleShape
            )
        ) {
            Text(
                modifier = Modifier.padding(25.dp, 5.dp, 25.dp, 5.dp),
                text = item.category,
                style = TextStyle(
                    fontSize = dpToSp(15.dp),
                    color = BasePurple,
                    textAlign = TextAlign.Left
                )
            )
        }
    }
}