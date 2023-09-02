package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.LighterPurple
import dgsw.stac.knowledgender.ui.theme.pretendard

@Composable
fun NotReadyDialog(openDialogCustom: MutableState<Boolean>) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        DialogView(openDialogCustom = openDialogCustom)
    }
}

@Composable
private fun DialogView(openDialogCustom: MutableState<Boolean>) {
    Surface(
        modifier = Modifier
            .width(328.dp)
            .height(168.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BaseText(
                modifier = Modifier.padding(top = 32.dp),
                text = "아직 준비 중인 기능입니다.",
                color = DarkestBlack,
                style = TextStyle(
                    fontFamily = pretendard,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            )
            Button(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .width(164.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(BasePurple),
                onClick = { openDialogCustom.value = false },
                contentPadding = PaddingValues(1.dp)
            ) {
                BaseText(
                    text = "확인",
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                )
            }

        }
    }
}