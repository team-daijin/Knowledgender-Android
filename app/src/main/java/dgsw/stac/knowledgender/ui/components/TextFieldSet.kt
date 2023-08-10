package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.pretendard

@Composable
fun TextFieldSet(
    textContent: String,
    textFieldPlaceHolder: String,
    errorMsg: String,
    value: MutableState<String>,
    isError: Boolean,
    isPw: Boolean = false
) {
    BaseText(
        text = textContent,
        color = DarkestPurple,
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium
        )
    )
    BaseTextField(
        modifier = Modifier.padding(top = 4.dp),
        value = value.value,
        placeHolder = textFieldPlaceHolder,
        onValueChange = { value.value = it },
        textFieldError = isError,
        isPw = isPw
    )
    ErrorText(
        modifier = Modifier.padding(top = 4.dp),
        text = errorMsg,
        isError = isError
    )
}