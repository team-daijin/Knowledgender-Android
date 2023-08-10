package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun BaseText(modifier: Modifier = Modifier, text: String, color: Color, style: TextStyle) {
    Text(
        text = text,
        modifier = modifier.wrapContentSize(),
        color = color,
        style = style
    )
}