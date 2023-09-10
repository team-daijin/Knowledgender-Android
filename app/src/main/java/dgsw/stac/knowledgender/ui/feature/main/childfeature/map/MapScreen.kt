package dgsw.stac.knowledgender.ui.feature.main.childfeature.map


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.model.MarkerItem
import dgsw.stac.knowledgender.ui.components.MapItemList
import dgsw.stac.knowledgender.ui.components.TextFieldSet
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkBlack
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val showDialog = mutableStateOf(false)

@Composable
fun MapScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: MapViewModel,
    onNavigationRequested: (String) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        body(viewModel)
    }
}

private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@ExperimentalMaterial3Api
fun body(viewModel: MapViewModel) {
    var openBottomSheet = rememberSaveable { mutableStateOf(false) }
    var reg = Utility.reg
    var bottomSheetState = rememberModalBottomSheetState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(reg, 10f)
    }
    var markerItems: List<MarkerItem> = listOf()
    LaunchedEffect(reg) {
        markerItems = viewModel.getMarkerItem(reg)
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                        openBottomSheet.value = false
                        viewModel.viewState = 0
                    }
                    else -> false
                }
                true
            },
        cameraPositionState = cameraPositionState,

        ) {
        for (markerItem in markerItems) {
            val markerReg = LatLng(markerItem.latitude, markerItem.longitude)
            Marker(state = MarkerState(position = markerReg),
                icon = BitmapFromVector(LocalContext.current, R.drawable.map_marker),
                onClick = {
                    openBottomSheet.value = true
                    viewModel.viewState = 1
                    openBottomSheet.value
                })
        }
    }
    IconButton(//저 그 버튼 누르면 현재위치 이동되는거 이렇게 처리해도 되겠죵 !
        modifier = Modifier.size(30.dp),
        onClick = {
            cameraPositionState.move((CameraUpdateFactory.newCameraPosition(cameraPositionState.position)))
        }) {
        Icon(painter = painterResource(R.drawable.map_location), contentDescription = null)
    }
    ModalBottomSheet(onDismissRequest = { openBottomSheet.value = false },
        sheetState = bottomSheetState,
        containerColor = Color.White,
        contentColor = DarkBlack,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        content = {
            when (viewModel.viewState) {
                1 -> {
                    BottomSheetDetailScreen(viewModel)
                }

                2 -> {
                    BottomSheetReservationScreen(viewModel)
                }

                else -> {
                    BottomSheetListScreen(viewModel)
                }
            }
        }
    )
}

@Composable
fun Dialog() {
    AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(24.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, containerColor = BasePurple
            ),
            shape = RoundedCornerShape(10.dp),
            enabled = true,
            onClick = {
                showDialog.value = false
            },
        ) {
            Text(
                text = "확인", color = Color.White, style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Medium, fontSize = 16.sp
                )
            )
        }
    }, title = {
        Text(
            text = "상담 예약 완료", color = DarkBlack, style = TextStyle(
                fontFamily = pretendard, fontWeight = FontWeight.SemiBold, fontSize = 24.sp
            )
        )
    }, text = {
        Text(
            text = "상담 예약이 완료되었습니다.", color = BaseBlack, style = TextStyle(
                fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = 16.sp
            )
        )
    })
}

@Composable
fun BottomSheetListScreen(viewModel: MapViewModel) {
    MapItemList(viewModel)
}
//뷰 보니까 게시글 > 메인 > 마이 순인거 같아서, 저는 ㅏ실 게시글이 하고 싶어서 넹 ! 마이 뷰는 많이 안나오긴 했는데
@Composable
fun BottomSheetDetailScreen(viewModel: MapViewModel) {
    Row(
        modifier = Modifier
            .height(190.dp)
            .fillMaxWidth()
            .padding(30.dp, 0.dp),
    ) {
        AsyncImage(
            model = viewModel.viewData.image,
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Column {
            Text(
                text = viewModel.viewData.name, color = DarkBlack, style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Medium, fontSize = 20.sp
                )
            )
            Text(
                text = viewModel.viewData.contact, color = BaseBlack, style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = 14.sp
                )
            )
            Text(
                text = viewModel.viewData.address, color = BaseBlack, style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = 14.sp
                )
            )
            if (viewModel.viewData.appointmentAvailable == true) {
                Button(
                    modifier = Modifier
                        .height(45.dp)
                        .width(330.dp)
                        .background(LightBlack),
                    onClick = {
                        viewModel.onColumnItemClicked(data = viewModel.viewData)
                    },
                ) {
                    Text(
                        text = "예약하기", color = Color.White, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp
                        )
                    )
                }
            } else {
                Text(
                    text = viewModel.viewData.introduce, color = DarkBlack, style = TextStyle(
                        fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = 14.sp
                    )
                )
            }
        }
    }
}

@Composable
fun BottomSheetReservationScreen(viewModel: MapViewModel) {
    val coroutineScope = rememberCoroutineScope()
    if (showDialog.value) {
        Dialog()
    }
    Row(
        modifier = Modifier
            .height(360.dp)
            .fillMaxWidth()
            .padding(30.dp, 0.dp)
    ) {
        AsyncImage(
            model = viewModel.viewData.image,
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Column {
            Text(
                text = viewModel.viewData.name, color = DarkBlack, style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Medium, fontSize = 20.sp
                )
            ) // 이름
            Text(
                text = viewModel.viewData.contact, color = BaseBlack, style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = 14.sp
                )
            ) // 전번
            Text(
                text = viewModel.viewData.address, color = BaseBlack, style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = 14.sp
                )
            ) // 주소
            Text(
                text = viewModel.viewData.introduce, color = DarkBlack, style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = 14.sp
                )
            ) // 설명
            TextFieldSet(textContent = "예약요일",
                textFieldPlaceHolder = "예약요일",
                errorMsg = "예약요일을 입력해주세요.",
                value = viewModel.date,
                isError = viewModel.dateError,
                onValueChange = {
                    viewModel.date = it
                })
            TextFieldSet(textContent = "예약시간",
                textFieldPlaceHolder = "예약시간",
                errorMsg = "예약시간을 입력해주세요.",
                value = viewModel.time,
                isError = viewModel.timeError,
                onValueChange = {
                    viewModel.time = it
                })
            TextFieldSet(textContent = "예약내용",
                textFieldPlaceHolder = "예약내용",
                errorMsg = "예약내용을 입력해주세요.",
                value = viewModel.content,
                isError = viewModel.contentError,
                onValueChange = {
                    viewModel.content = it
                })
            if (viewModel.viewData.appointmentAvailable == true) {
                Button(
                    modifier = Modifier
                        .height(45.dp)
                        .width(330.dp)
                        .background(BasePurple),
                    onClick = {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) {
                                try {
                                    viewModel.postReservation(viewModel.viewData.id)
                                } catch (e: Exception) {
                                    Log.d("TAG", "${e.message}")
                                }
                            }
                        }
                        showDialog.value = true
                    },
                ) {
                    Text(
                        text = "예약하기", color = Color.White, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp
                        )
                    )
                }
            } else {
//                뭔가를 넣겠징....?
            }
        }
    }
}