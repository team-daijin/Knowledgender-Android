package dgsw.stac.knowledgender.ui.feature.mypage

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.navigation.Route.HOME
import dgsw.stac.knowledgender.remote.Profile
import dgsw.stac.knowledgender.ui.components.BaseText
import dgsw.stac.knowledgender.ui.components.RowButton
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
    val openDialog = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        //verticalArrangement = Arrangement.SpaceEvenly
    ) {
        state?.let {
            Management(it, onNavigationRequested, viewModel)
        } ?: run {
//            Column(
//                Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                BaseDialog(
//                    openDialogCustom = openDialog,
//                    onLoginRequested = {  },
//                    title = "로그인 하시겠습니까?",
//                    description = "로그인이 필요한 기능입니다."
//                )
//            }
        }
    }
}

@Composable
private fun UserProfile() {


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
        //RowButton(clickable = { }, text = "SNS 계정 연동 관리", textColor = LightBlack)
    }
}

@Composable
private fun Management(
    state: Profile,
    onNavigationRequested: (String) -> Unit,
    viewModel: MyPageViewModel
) {
    Column(modifier = Modifier.padding(top = 60.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(shape = RoundedCornerShape(100.dp)) {
                Image(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 20.dp),
                    painter = painterResource(id = R.drawable.profile_ex),
                    contentDescription = "profile"
                )
            }
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
        Spacer(modifier = Modifier.height(32.dp))
        BaseText(
            Modifier
                .padding(bottom = 8.dp),
            text = "예약한 상담일정",
            color = DarkestBlack,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = dpToSp(16.dp)
            )
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "등록된 상담일정이 없습니다.")
            Image(
                painter = painterResource(id = R.drawable.noschedule),
                contentDescription = "noSchedule"
            )
        }
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
