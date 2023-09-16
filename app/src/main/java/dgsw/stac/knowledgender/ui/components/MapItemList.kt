package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dgsw.stac.knowledgender.remote.AppointmentResponse
import dgsw.stac.knowledgender.ui.feature.main.childfeature.map.MapViewModel
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.pretendard

@Composable
fun MapItemList(viewModel: MapViewModel) {
    val appointmentItems by viewModel.appointmentView.collectAsState()
    appointmentItems?.let {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().height(200.dp)
        ) {
            items(it) { item ->
                MapItemView(item)
            }
        }
    } ?: run {
        NoData()
    }
}

@Composable
fun MapItemView(item: AppointmentResponse) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
        ) {
            Text(
                text = item.name,
                color = DarkestBlack,
                style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Medium, fontSize = 20.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.address,
                color = BaseBlack,
                style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.contact,
                color = BaseBlack,
                style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = 14.sp
                )
            )
//            if (item.appointmentAvailable) {
//                Button(
//                    modifier = Modifier
//                        .heightIn(30.dp)
//                        .widthIn(200.dp)
//                        .padding(top = 8.dp),
//                    onClick = {
//                        viewModel.onColumnItemClicked(item)
//                    },
//                    shape = RoundedCornerShape(5.dp),
//                    colors = ButtonDefaults.buttonColors(BasePurple)
//                ) {
//                    Text(
//                        text = "예약하기",
//                        style = TextStyle(
//                            fontFamily = pretendard,
//                            fontWeight = FontWeight.Normal,
//                            fontSize = 20.sp
//                        )
//                    )
//                }
//            } else {
//                Text(
//                    modifier = Modifier
//                        .height(40.dp)
//                        .width(200.dp),
//                    text = item.introduce,
//                    color = DarkestBlack,
//                    style = TextStyle(
//                        fontFamily = pretendard,
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 14.sp
//                    )
//                )
//            }
        }
    }
}
