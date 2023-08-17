package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dgsw.stac.knowledgender.ui.theme.LightRed
import dgsw.stac.knowledgender.ui.theme.pretendard

@Composable
fun ErrorText(modifier: Modifier = Modifier, isError: Boolean, text: String) {
    Text(
        text = text,
        modifier = modifier
            .wrapContentSize()
            .alpha(if (isError) 1f else 0f),
        color = LightRed,
        style = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    )
}