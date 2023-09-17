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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import dgsw.stac.knowledgender.navigation.Route
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
import dgsw.stac.knowledgender.util.dpToSp


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
        Image(
            painter = painterResource(id = R.drawable.knowlegender_logo_filled),
            contentDescription = "logo",
            modifier = Modifier.width(30.dp)

        )
        BaseText(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = R.string.register),
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = dpToSp(20.dp)
            )
        )
        BaseText(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(id = R.string.register_description),
            color = DarkBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = dpToSp(14.dp)
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
            textContent = stringResource(id = R.string.id),
            textFieldPlaceHolder = stringResource(id = R.string.id_placeholder),
            errorMsg = stringResource(id = R.string.id_duplicated),
            value = viewModel.id.collectAsState().value,
            isError = viewModel.idError.value,
            onValueChange = { viewModel.id.value = it }
        )
        TextFieldSet(
            textContent = stringResource(id = R.string.pw),
            textFieldPlaceHolder = stringResource(id = R.string.pw_placeholder),
            errorMsg = stringResource(id = R.string.pw_unfulfilled),
            value = viewModel.pw.collectAsState().value,
            isError = viewModel.pwError.value,
            isPw = true,
            onValueChange = { viewModel.pw.value = it }
        )
        TextFieldSet(
            textContent = stringResource(id = R.string.pw_check),
            textFieldPlaceHolder = stringResource(id = R.string.pw_check_placeholder),
            errorMsg = stringResource(id = R.string.pw_check_dismatch),
            value = viewModel.pwCheck.collectAsState().value,
            isError = viewModel.pwCheckError.value,
            isPw = true,
            onValueChange = { viewModel.pwCheck.value = it }
        )
        BaseText(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = R.string.name),
            color = DarkestPurple,
            style = TextStyle(
                fontSize = dpToSp(16.dp),
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium
            )
        )
        BaseTextField(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            value = viewModel.name.collectAsState().value,
            placeHolder = stringResource(id = R.string.name_placeholder),
            onValueChange = {
                viewModel.name.value = it
            },
        )
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 40.dp),
            Arrangement.SpaceEvenly
        ) {
            Column {
                BaseText(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = stringResource(id = R.string.age),
                    color = BasePurple,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = dpToSp(16.dp)
                    )
                )
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .width(100.dp)
                        .border(width = 1.dp, color = BasePurple, shape = RoundedCornerShape(5.dp))
                ) {
                    FVerticalWheelPicker(
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
            Column {
                BaseText(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = stringResource(id = R.string.gender),
                    color = BasePurple,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = dpToSp(16.dp)
                    )
                )
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = { viewModel.gender.value = "남성" },
                        Modifier
                            .height(40.dp),
                        shape = RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (viewModel.gender.collectAsState().value == "남성") BasePurple else Color.Transparent
                        ),
                        border = if (viewModel.gender.collectAsState().value == "남성") null else BorderStroke(
                            1.dp,
                            LightSky
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.gender_male),
                            style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = dpToSp(16.dp)
                            ),
                            color = if (viewModel.gender.collectAsState().value == "남성") {
                                Color.White
                            } else {
                                LightBlack
                            }
                        )
                    }
                    Button(
                        onClick = { viewModel.gender.value = "여성" },
                        Modifier
                            .height(40.dp),
                        shape = RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (viewModel.gender.collectAsState().value == "여성") BasePurple else Color.Transparent
                        ),
                        border = if (viewModel.gender.collectAsState().value == "여성") null else BorderStroke(
                            1.dp,
                            LightSky
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.gender_female),
                            style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = dpToSp(16.dp)
                            ),
                            color = if (viewModel.gender.collectAsState().value == "여성") {
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
                if (viewModel.enabledButton.value) {
                    viewModel.registerProcess {
                        onNavigationRequested(Route.LOGIN)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(45.dp),
            color = ButtonDefaults.buttonColors(
                containerColor = if (viewModel.enabledButton.collectAsState().value) {
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
                fontSize = dpToSp(16.dp)
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
                fontSize = dpToSp(16.dp)
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