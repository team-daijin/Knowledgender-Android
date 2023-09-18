package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.feature.main.CARDNEWS
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.BODY
import dgsw.stac.knowledgender.util.CRIME
import dgsw.stac.knowledgender.util.HEART
import dgsw.stac.knowledgender.util.RELATIONSHIP
import dgsw.stac.knowledgender.util.dpToSp

@Composable
fun CategoryIcon(category: String,onNavigateTo: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.size(30.dp).clickable { onNavigateTo("$CARDNEWS/${category}") },
            painter = when(category){
                HEART -> painterResource(id = R.drawable.heart)
                BODY -> painterResource(id = R.drawable.body)
                CRIME -> painterResource(id = R.drawable.crime)
                RELATIONSHIP -> painterResource(id = R.drawable.relationship)
                else -> painterResource(id = R.drawable.equality)
            },
            contentDescription = "heart"
        )
        Text(
            text = category,
            color = BaseBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = dpToSp(
                    dp = 14.dp
                )
            )
        )
    }
}