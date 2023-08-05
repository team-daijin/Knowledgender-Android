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
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.Route
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkBlack
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.LightSky
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.Utility.BaseButton
import dgsw.stac.knowledgender.util.Utility.BaseText
import dgsw.stac.knowledgender.util.Utility.BaseTextField
import dgsw.stac.knowledgender.util.Utility.TextFieldSet


@Composable
fun RegisterScreen(viewModel: RegisterViewModel, modifier: Modifier = Modifier,onNavigationRequested: (String)->Unit) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 32.dp, top = 60.dp, end = 32.dp, bottom = 32.dp)
            .verticalScroll(scrollState),
        Arrangement.SpaceBetween
    ) {
        Header()
        Body(viewModel)
        Footer(viewModel,onNavigationRequested)
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
                contentDescription = "",
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
            text = "회원가입",
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        BaseText(
            modifier = Modifier.padding(top = 4.dp),
            text = "회원가입을 위해 필요한 정보를 입력해 주세요",
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
    Column(
    ) {
        TextFieldSet(
            textContent = "아이디",
            textFieldPlaceHolder = "아이디를 입력해주세요",
            errorMsg = "이미 존재하는 아이디입니다",
            value = viewModel.id,
            isError = viewModel.idError
        )
        TextFieldSet(
            textContent = "비밀번호",
            textFieldPlaceHolder = "비밀번호를 입력해주세요",
            errorMsg = "영문, 숫자, 특수기호 포함 8자리 이상으로 입력해주세요",
            value = viewModel.pw,
            isError = viewModel.pwError,
            isPw = true
        )
        BaseText(
            modifier = Modifier.padding(top = 8.dp),
            text = "이름",
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
            placeHolder = "이름을 입력해주세요",
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
                    text = "나이(세)",
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
                        count = 100
                    ) {
                        Text(it.toString())
                        viewModel.age.value = it
                    }
                }
            }
            Column(

            ) {
                BaseText(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "성별",
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
                            text = "남성",
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
                            text = "여성",
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
private fun Footer(viewModel: RegisterViewModel,onNavigationRequested: (String) -> Unit) {
    Column {
        BaseButton(
            onClick = {
//                viewModel.registerPOST(
//                    RegisterRequest(
//                        accountId = viewModel.id.value,
//                        password = viewModel.pw.value,
//                        name = viewModel.name.value,
//                        age = viewModel.age.value,
//                        gender = viewModel.gender.value
//                    )
//                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(32.dp)
                .padding(top = 22.dp),
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