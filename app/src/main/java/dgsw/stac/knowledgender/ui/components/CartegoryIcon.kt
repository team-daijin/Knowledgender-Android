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
import dgsw.stac.knowledgender.navigation.Route.CARDNEWS
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.Category
import dgsw.stac.knowledgender.util.dpToSp

@Composable
fun CategoryIcon(category: String,onNavigateTo: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.size(30.dp).clickable { onNavigateTo("$CARDNEWS/${category}") },
            painter = when(category){
                Category.HEART.str -> painterResource(id = R.drawable.heart)
                Category.BODY.str -> painterResource(id = R.drawable.body)
                Category.RELATION.str -> painterResource(id = R.drawable.relationship)
                Category.VIOLENCE.str -> painterResource(id = R.drawable.crime)
                else -> painterResource(id = R.drawable.equality)
            },
            contentDescription = null
        )
        Text(
            text = when(category){
                Category.HEART.str -> "마음"
                Category.BODY.str -> "신체"
                Category.RELATION.str -> "관계"
                Category.VIOLENCE.str -> "폭력"
                else -> "평등"
            },
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