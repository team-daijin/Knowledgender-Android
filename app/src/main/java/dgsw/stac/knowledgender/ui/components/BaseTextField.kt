package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestRed
import dgsw.stac.knowledgender.ui.theme.LighterBlack
import dgsw.stac.knowledgender.ui.theme.LighterSky
import dgsw.stac.knowledgender.ui.theme.pretendard

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeHolder: String,
    textFieldError: Boolean = false,
    onValueChange: (String) -> Unit,
    isPw: Boolean = false
) {
    //textfield's state
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    //password toggle's state
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val image = if (passwordVisible)
        Icons.Filled.Visibility
    else Icons.Filled.VisibilityOff

    val description = if (passwordVisible) "Hide password" else "Show password"

    BasicTextField(
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        visualTransformation =
        if (isPw && (!passwordVisible)) PasswordVisualTransformation()
        else VisualTransformation.None,
        decorationBox = { innerTextField ->
            Box(
                modifier = modifier
                    .heightIn(44.dp)
                    .border(
                        1.dp,
                        color = if (textFieldError) DarkestRed else if (isFocused) BasePurple else LighterSky,
                        RoundedCornerShape(8.dp)
                    ),
                Alignment.CenterStart,

                ) {
                Box(modifier = Modifier.padding(start = 16.dp)) {
                    if (value.isEmpty()) {
                        Text(
                            placeHolder,
                            color = LighterBlack,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier
                        )
                    }
                    innerTextField()
                }

                if (isPw) {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }, modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 12.dp)
                    ) {
                        Icon(imageVector = image, description)
                    }
                }
            }
        }
    )
}
