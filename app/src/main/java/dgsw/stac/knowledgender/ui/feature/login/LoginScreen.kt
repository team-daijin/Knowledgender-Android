package dgsw.stac.knowledgender.ui.feature.login

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.Route
import dgsw.stac.knowledgender.ui.components.BaseButton
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.components.TextFieldSet
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.LightSky
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.Utility.getStringFromResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onNavigationRequested: (String) -> Unit
) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        Arrangement.SpaceEvenly
    ) {
        Header()
        Body(viewModel, onNavigationRequested = onNavigationRequested)
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
private fun Body(viewModel: LoginViewModel, onNavigationRequested: (String) -> Unit) {
    Column() {
        TextFieldSet(
            textContent = getStringFromResource(value = R.string.id),
            textFieldPlaceHolder = getStringFromResource(value = R.string.id_placeholder),
            errorMsg = getStringFromResource(value = R.string.id_wrong),
            value = viewModel.id,
            isError = viewModel.idError
        )
        TextFieldSet(
            textContent = getStringFromResource(value = R.string.pw),
            textFieldPlaceHolder = getStringFromResource(value = R.string.pw_placeholder),
            errorMsg = getStringFromResource(value = R.string.pw_wrong),
            value = viewModel.pw,
            isError = viewModel.pwError,
            isPw = true
        )
        BaseButton(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.loginPOST()
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(32.dp)
                .padding(top = 22.dp),
            color = ButtonDefaults.buttonColors(
                containerColor = if (viewModel.id.value.isNotEmpty() && viewModel.pw.value.isNotEmpty()) BasePurple else LightSky
            ),
            text = getStringFromResource(value = R.string.login),
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
                        append(getStringFromResource(value = R.string.register))
                    }
                },
                onClick = { onNavigationRequested(Route.REGISTER) },
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
                onClick = { },
                style = TextStyle(
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
private fun Footer() {

}


@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun GreetingPreview() {
    KnowledgenderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            LoginScreen(viewModel = LoginViewModel(), onNavigationRequested = {

            })
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
            LoginScreen(viewModel = LoginViewModel(), onNavigationRequested = {

            })
        }
    }
}

