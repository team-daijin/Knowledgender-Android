package dgsw.stac.knowledgender.ui.feature.login

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.navigation.Route
import dgsw.stac.knowledgender.ui.components.BaseButton
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.components.TextFieldSet
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.LightSky
import dgsw.stac.knowledgender.ui.theme.pretendard


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
            text = stringResource(R.string.title),
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
    val isValidLoginData by viewModel.enabledButton.collectAsState()

    Column {
        TextFieldSet(
            textContent = stringResource(id = R.string.id),
            textFieldPlaceHolder = stringResource(id = R.string.id_placeholder),
            errorMsg = stringResource(id = R.string.id_wrong),
            value = viewModel.id,
            isError = viewModel.idError,
            onValueChange = {
                viewModel.id = it
            }
        )
        TextFieldSet(
            textContent = stringResource(id = R.string.pw),
            textFieldPlaceHolder = stringResource(id = R.string.pw_placeholder),
            errorMsg = stringResource(id = R.string.pw_wrong),
            value = viewModel.pw,
            isError = viewModel.pwError,
            isPw = true,
            onValueChange = {
                viewModel.pw = it
            }
        )
        BaseButton(
            onClick = {
                if (isValidLoginData) {
                    viewModel.loginPOST(onNavigationRequested)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(32.dp)
                .padding(top = 22.dp),
            color = ButtonDefaults.buttonColors(
                containerColor = if (isValidLoginData) BasePurple else LightSky
            ),
            text = stringResource(id = R.string.login),
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
                        append(stringResource(id = R.string.register))
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
