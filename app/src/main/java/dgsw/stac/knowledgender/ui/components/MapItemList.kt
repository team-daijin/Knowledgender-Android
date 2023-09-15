package dgsw.stac.knowledgender.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dgsw.stac.knowledgender.remote.AppointmentResponse
import dgsw.stac.knowledgender.ui.feature.main.childfeature.map.MapViewModel
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.pretendard

@Composable
fun MapItemList(viewModel: MapViewModel) {
    val appointmentItems = viewModel.appointmentView
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(appointmentItems) { item ->
            MapItemView(item, viewModel)
        }
    }
}

@Composable
fun MapItemView(item: AppointmentResponse, viewModel: MapViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(24.dp, 0.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            AsyncImage(
                model = item.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
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
                if (item.appointmentAvailable) {
                    Button(
                        modifier = Modifier
                            .height(40.dp)
                            .width(200.dp)
                            .background(BasePurple),
                        onClick = {
                            viewModel.onColumnItemClicked(item)
                        },
                    ) {
                        Text(
                            text = "예약하기",
                            style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp
                            )
                        )
                    }
                } else {
                    Text(
                        modifier = Modifier
                            .height(40.dp)
                            .width(200.dp),
                        text = item.introduce,
                        color = DarkestBlack,
                        style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }
}
