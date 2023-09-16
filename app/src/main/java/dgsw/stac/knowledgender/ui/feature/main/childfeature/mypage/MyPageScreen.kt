package dgsw.stac.knowledgender.ui.feature.main.childfeature.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dgsw.stac.knowledgender.remote.Profile
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.components.RowButton
import dgsw.stac.knowledgender.ui.feature.main.HOME
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.DarkestRed
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.LighterBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.dpToSp

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel,
    onNavigationRequested: (String) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getProfile()
    }

    val state by viewModel.profile.collectAsState()

    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        state?.let {

            UserProfile(it)
            Management(onNavigationRequested, viewModel)

        } ?: run {

        }
    }
}

@Composable
private fun UserProfile(state: Profile) {
    Column {
        BaseText(
            text = state.name,
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = dpToSp(16.dp)
            )
        )
        BaseText(
            text = "${state.age}세 | ${state.gender}",
            color = LighterBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = dpToSp(14.dp)
            )
        )
    }
}

@Composable
private fun UserInfo(state: Profile) {
    Column {
        BaseText(
            Modifier.padding(bottom = 8.dp),
            text = "개인정보",
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = dpToSp(16.dp)
            )
        )
        RowButton(clickable = { }, text = "회원명 수정", textColor = LightBlack)
        RowButton(clickable = { }, text = "아이디, 비밀번호 수정", textColor = LightBlack)
        RowButton(clickable = { }, text = "SNS 계정 연동 관리", textColor = LightBlack)
    }
}

@Composable
private fun Management(onNavigationRequested: (String) -> Unit, viewModel: MyPageViewModel) {
    Column {
        BaseText(
            Modifier.padding(bottom = 8.dp),
            text = "계정관리",
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = dpToSp(16.dp)
            )
        )
        RowButton(clickable = {
            viewModel.logout()
            onNavigationRequested(HOME)
        }, text = "로그아웃", textColor = DarkestRed)
        RowButton(clickable = {
            viewModel.userDelete()
            onNavigationRequested(HOME)
        }, text = "회원탈퇴", textColor = DarkestRed)

    }
}
