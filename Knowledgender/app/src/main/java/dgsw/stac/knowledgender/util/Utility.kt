package dgsw.stac.knowledgender.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dgsw.stac.knowledgender.ui.theme.DarkestPurple
import dgsw.stac.knowledgender.ui.theme.Typography


object Utility {
    const val REGISTERPOST = "대충 통신 주소"
    const val LOGINGET = ""

    @Composable
    fun BaseText(modifier: Modifier = Modifier, text: String) {
        Text(
            text = text,
            modifier = modifier
                .heightIn(32.dp)
                .widthIn(32.dp),
            color = DarkestPurple,
            style = Typography.labelMedium
        )
    }

    @Composable
    fun DefaultTextField(
        modifier: Modifier = Modifier,
        id: String,
        placeHolder: String,
        onValueChange: (String) -> Unit
    ) {
        OutlinedTextField(
            value = id,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .heightIn(32.dp),
            placeholder = { Text(text = placeHolder) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color.Magenta
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
    
    @Composable
    fun BaseButton() {
        Button(onClick = {  }) {
            
        }
    }
    
}