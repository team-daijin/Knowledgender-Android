package dgsw.stac.knowledgender.feature.login

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.LightSky
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.Utility.BaseButton
import dgsw.stac.knowledgender.util.Utility.BaseText
import dgsw.stac.knowledgender.util.Utility.BaseTextField
import dgsw.stac.knowledgender.util.Utility.ErrorText

class LoginActivity : ComponentActivity() {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KnowledgenderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    LoginView(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun LoginView(modifier: Modifier = Modifier, viewModel: LoginViewModel = LoginViewModel()) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        Arrangement.SpaceEvenly
    ) {
        Header()
        Body(viewModel)
        Footer()
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseText(
            text = "우리가 몰랐던 성지식이",
            color = DarkestPurple,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )
        )
        BaseText(
            text = "알고싶었성",
            color = DarkestPurple,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp
            )
        )
    }
}

@Composable
private fun Body(viewModel: LoginViewModel) {
    Column() {
        BaseText(
            text = "아이디",
            color = DarkestPurple,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium
            )
        )
        BaseTextField(
            modifier = Modifier.padding(top = 4.dp),
            value = viewModel.id.value,
            placeHolder = "아이디를 입력해주세요",
            onValueChange = { viewModel.id.value = it },
            textFieldError = viewModel.idError,
        )
        ErrorText(
            modifier = Modifier.padding(top = 4.dp),
            text = "아이디를 다시 입력해주세요.",
            isError = viewModel.idError
        )
        BaseText(
            modifier = Modifier.padding(top = 8.dp),
            text = "비밀번호",
            color = DarkestPurple,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium
            )
        )
        BaseTextField(
            modifier = Modifier.padding(top = 4.dp),
            value = viewModel.pw.value,
            placeHolder = "비밀번호를 입력해주세요",
            onValueChange = { viewModel.pw.value = it },
            textFieldError = viewModel.pwError,
            isPw = true
        )
        ErrorText(
            modifier = Modifier
                .padding(top = 4.dp),
            text = "비밀번호를 다시 입력해주세요.",
            isError = viewModel.pwError
        )
        BaseButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(32.dp)
                .padding(top = 22.dp),
            color = ButtonDefaults.buttonColors(
                containerColor = if (
                    viewModel.id.value.isNotEmpty() && viewModel.pw.value.isNotEmpty()) BasePurple else LightSky
            ),
            text = "로그인",
            textColor = Color.White,
            textStyle = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp),
            Arrangement.SpaceBetween
        ) {
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = BasePurple)) {
                        append("회원가입")
                    }
                },
                onClick = {},
                style = TextStyle(
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                )
            )
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = LightBlack)) {
                        append("비밀번호를 잊어버렸어요")
                    }
                },
                onClick = {},
                style = TextStyle(

                )
            )
        }

    }
}

@Composable
private fun Footer(){

}


@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun GreetingPreview() {
    KnowledgenderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            LoginView()
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GreetingDarkPreview() {
    KnowledgenderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            LoginView()
        }
    }
}

