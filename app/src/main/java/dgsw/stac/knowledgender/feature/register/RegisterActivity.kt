package dgsw.stac.knowledgender.feature.register

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import dgsw.stac.knowledgender.util.Utility.ErrorText
import retrofit2.http.Header

class RegisterActivity : ComponentActivity() {
    private val viewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KnowledgenderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterView(viewModel)
                }
            }
        }
    }
}

@Composable
fun RegisterView(viewModel: RegisterViewModel, modifier: Modifier = Modifier) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 32.dp, top = 60.dp, end = 32.dp, bottom = 32.dp),
        Arrangement.SpaceBetween
    ) {
        Header()
        Body(viewModel)
        Footer(viewModel)
    }

}

@Composable
private fun Header() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Box(modifier = Modifier.size(50.dp))
        BaseText(
            text = "회원가입",
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        BaseText(
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
            textFieldError = viewModel.pwError
        )
        ErrorText(
            modifier = Modifier
                .padding(top = 4.dp),
            text = "비밀번호를 다시 입력해주세요.",
            isError = viewModel.pwError
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
            onValueChange = { viewModel.name.value = it }
        )

    }
}

@Composable
private fun Footer(viewModel: RegisterViewModel){
    Column {
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
            text = "완료하기",
            textColor = Color.White,
            textStyle = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.SemiBold,
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
            RegisterView(RegisterViewModel())
        }
    }
}