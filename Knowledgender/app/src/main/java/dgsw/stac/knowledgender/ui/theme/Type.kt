package dgsw.stac.knowledgender.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dgsw.stac.knowledgender.R

val pretendard = FontFamily(
    Font(R.font.pretendard_light,FontWeight.Light),
    Font(R.font.pretendard_regular,FontWeight.Normal),
    Font(R.font.pretendard_medium,FontWeight.Medium),
    Font(R.font.pretendard_semibold,FontWeight.SemiBold),
    Font(R.font.pretendard_bold,FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    labelMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )


)