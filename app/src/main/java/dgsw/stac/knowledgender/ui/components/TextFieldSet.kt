package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.dpToSp

@Composable
fun TextFieldSet(
    textContent: String,
    textFieldPlaceHolder: String,
    errorMsg: String,
    value: String,
    isError: Boolean = false,
    isPw: Boolean = false,
    onValueChange: (String) -> Unit
) {
    BaseText(
        text = textContent,
        color = DarkestPurple,
        style = TextStyle(
            fontSize = dpToSp(16.dp),
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium
        )
    )
    BaseTextField(
        modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
        value = value,
        placeHolder = textFieldPlaceHolder,
        onValueChange = onValueChange,
        textFieldError = isError,
        isPw = isPw
    )
    ErrorText(
        modifier = Modifier.padding(top = 4.dp),
        text = errorMsg,
        isError = isError
    )
}