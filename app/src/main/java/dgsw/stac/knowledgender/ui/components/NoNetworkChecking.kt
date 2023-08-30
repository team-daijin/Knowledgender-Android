package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.theme.LighterRed
import dgsw.stac.knowledgender.ui.theme.pretendard

@Composable
fun NoNetworkChecking(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = R.drawable.nowifi), contentDescription = "noWifi")
        BaseText(
            text = "인터넷에 연결되어 있지 않습니다.",
            color = LighterRed,
            style = TextStyle(
                fontFamily = pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
    }
}