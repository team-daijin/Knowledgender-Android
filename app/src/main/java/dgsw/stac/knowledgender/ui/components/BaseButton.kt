package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: ButtonColors,
    text: String,
    textColor: Color,
    textStyle: TextStyle
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = color,
        contentPadding = PaddingValues(vertical = 14.dp)
    ) {
        BaseText(text = text, color = textColor, style = textStyle)
    }
}