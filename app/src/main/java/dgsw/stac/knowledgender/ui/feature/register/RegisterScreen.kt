package dgsw.stac.knowledgender.ui.feature.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sd.lib.compose.wheel_picker.FVerticalWheelPicker
import com.sd.lib.compose.wheel_picker.rememberFWheelPickerState
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.Route
import dgsw.stac.knowledgender.ui.components.BaseButton
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.components.BaseTextField
import dgsw.stac.knowledgender.ui.components.TextFieldSet
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkBlack
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.LightSky
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.Utility.getStringFromResource


@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    modifier: Modifier = Modifier,
    onNavigationRequested: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 32.dp, top = 60.dp, end = 32.dp, bottom = 32.dp)
            .verticalScroll(scrollState)
    ) {
        Header()
        Box(modifier = Modifier.height(32.dp))
        Body(viewModel)
        Box(modifier = Modifier.height(32.dp))
        Footer(viewModel, onNavigationRequested)
    }

}

@Composable
private fun Header() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_register),
                contentDescription = "logo",
                modifier = Modifier.height(50.dp)

            )
            BaseText(
                modifier = Modifier.padding(start = 8.dp),
                text = "알고싶었성",
                color = DarkestPurple,
                style = TextStyle(
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            )
        }

        BaseText(
            modifier = Modifier.padding(top = 8.dp),
            text = getStringFromResource(value = R.string.register),
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        BaseText(
            modifier = Modifier.padding(top = 4.dp),
            text = getStringFromResource(value = R.string.register_description),
            color = DarkBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
        )
    }
}

@Composable
private fun Body(viewModel: RegisterViewModel) {
    val wheelPickerState = rememberFWheelPickerState()
    Column(
    ) {
        TextFieldSet(
            textContent = getStringFromResource(value = R.string.id),
            textFieldPlaceHolder = getStringFromResource(value = R.string.id_placeholder),
            errorMsg = getStringFromResource(value = R.string.id_duplicated),
            value = viewModel.id,
            isError = viewModel.idError
        )
        TextFieldSet(
            textContent = getStringFromResource(value = R.string.pw),
            textFieldPlaceHolder = getStringFromResource(value = R.string.pw_placeholder),
            errorMsg = getStringFromResource(value = R.string.pw_unfulfilled),
            value = viewModel.pw,
            isError = viewModel.pwError,
            isPw = true
        )
        TextFieldSet(
            textContent = getStringFromResource(value = R.string.pw_check),
            textFieldPlaceHolder = getStringFromResource(value = R.string.pw_check_placeholder),
            errorMsg = getStringFromResource(value = R.string.pw_check_dismatch),
            value = viewModel.pwCheck,
            isError = viewModel.pwCheckError,
            isPw = true
        )
        BaseText(
            modifier = Modifier.padding(top = 8.dp),
            text = getStringFromResource(value = R.string.name),
            color = DarkestPurple,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium
            )
        )
        BaseTextField(
            modifier = Modifier.padding(top = 4.dp),
            value = viewModel.name.value,
            placeHolder = getStringFromResource(value = R.string.name_placeholder),
            onValueChange = {
                viewModel.name.value = it
            },
        )
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 40.dp),
            Arrangement.SpaceBetween
        ) {
            Column(

            ) {
                BaseText(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = getStringFromResource(value = R.string.age),
                    color = BasePurple,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                )
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .border(width = 1.dp, color = BasePurple, shape = RoundedCornerShape(5.dp))
                ) {
                    FVerticalWheelPicker(
                        modifier = Modifier.width(158.dp),
                        count = 100,
                        state = wheelPickerState
                    ) { index ->
                        Text(index.toString())
                        LaunchedEffect(state) {
                            snapshotFlow { state.currentIndex }
                                .collect { ageValue ->
                                    viewModel.age.value = ageValue
                                }
                        }
                    }
                }
            }
            Column(

            ) {
                BaseText(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = getStringFromResource(value = R.string.gender),
                    color = BasePurple,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                )
                Row() {
                    Button(
                        onClick = { viewModel.gender.value = "남성" },
                        Modifier
                            .width(80.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (viewModel.gender.value == "남성") BasePurple else Color.Transparent
                        ),
                        border = if (viewModel.gender.value == "남성") null else BorderStroke(
                            1.dp,
                            LightSky
                        )
                    ) {
                        Text(
                            text = getStringFromResource(value = R.string.gender_male),
                            style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            ),
                            color = if (viewModel.gender.value == "남성") {
                                Color.White
                            } else {
                                LightBlack
                            }
                        )
                    }
                    Button(
                        onClick = { viewModel.gender.value = "여성" },
                        Modifier
                            .width(80.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (viewModel.gender.value == "여성") BasePurple else Color.Transparent
                        ),
                        border = if (viewModel.gender.value == "여성") null else BorderStroke(
                            1.dp,
                            LightSky
                        )
                    ) {
                        Text(
                            text = getStringFromResource(value = R.string.gender_female),
                            style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            ),
                            color = if (viewModel.gender.value == "여성") {
                                Color.White
                            } else {
                                LightBlack
                            }
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun Footer(viewModel: RegisterViewModel, onNavigationRequested: (String) -> Unit) {
    Column(Modifier.wrapContentHeight()) {
        BaseButton(
            onClick = {
                viewModel.registerPOST()

            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(45.dp),
            color = ButtonDefaults.buttonColors(
                containerColor = if (
                    viewModel.id.value.isNotEmpty() &&
                    viewModel.pw.value.isNotEmpty() &&
                    viewModel.name.value.isNotEmpty() &&
                    (viewModel.age.value != 0) &&
                    viewModel.gender.value.isNotEmpty()
                ) {
                    BasePurple
                } else {
                    LightSky
                }
            ),
            text = "완료하기",
            textColor = Color.White,
            textStyle = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        )
        ClickableText(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = BasePurple)) {
                    append("로그인")
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.End),
            onClick = { onNavigationRequested(Route.LOGIN) },
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    KnowledgenderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RegisterScreen(RegisterViewModel()) {

            }
        }
    }
}