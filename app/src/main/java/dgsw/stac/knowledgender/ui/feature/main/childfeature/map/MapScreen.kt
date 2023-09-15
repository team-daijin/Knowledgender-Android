package dgsw.stac.knowledgender.ui.feature.main.childfeature.map


import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.model.MarkerItem
import dgsw.stac.knowledgender.ui.components.MapItemList
import dgsw.stac.knowledgender.ui.components.TextFieldSet
import dgsw.stac.knowledgender.ui.feature.main.childfeature.home.Body
import dgsw.stac.knowledgender.ui.feature.register.RegisterScreen
import dgsw.stac.knowledgender.ui.feature.register.RegisterViewModel
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkBlack
import dgsw.stac.knowledgender.ui.theme.KnowledgenderTheme
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val showDialog = mutableStateOf(false)


@Composable
fun MapScreen(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
    viewModel: MapViewModel,
    onNavigationRequested: (String) -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        viewModel.mLocationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5 * 1000
        }
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = BottomSheetDefaults.SheetPeekHeight,
            sheetSwipeEnabled = true,
            sheetContainerColor = Color.White,
            contentColor = DarkBlack,
            sheetDragHandle = { BottomSheetDefaults.DragHandle() },
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
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
            },
        ) {
            Scaffold {
                    padding ->
                Box(modifier = Modifier.padding(padding)) {
                    body(viewModel,coroutineScope, bottomSheetScaffoldState)
                }
            }

        }
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
fun body(viewModel: MapViewModel, scope: CoroutineScope, state: BottomSheetScaffoldState) {
    val reg by viewModel.reg.collectAsState()
    viewModel.startLocationUpdates()
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                scrollGesturesEnabled = true,
                zoomControlsEnabled = true,
                zoomGesturesEnabled = true,
                myLocationButtonEnabled = true
            )
        )
    }
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(mapType = MapType.NORMAL, maxZoomPreference = 30f, minZoomPreference = 1f)
        )
    }
    val zoomLevel = remember { mutableStateOf(15f) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(reg, zoomLevel.value)
        Log.d("euya", position.target.toString())
    }

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { cameraPositionState.position.zoom }
            .collect {
                zoomLevel.value = it
            }
    }

    var markerItems: List<MarkerItem> = listOf()
    LaunchedEffect(reg) {
        markerItems = viewModel.getMarkerItem(reg)
        cameraPositionState.position = CameraPosition.fromLatLngZoom(reg, zoomLevel.value)
    }

    GoogleMap(
        properties = mapProperties,
        uiSettings = uiSettings,
        cameraPositionState = cameraPositionState,
        modifier = Modifier
            .fillMaxSize()
//            .pointerInteropFilter {
//                when (it.action) {
//                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
//                        viewModel.viewState = 0
//                    }
//
//                    else -> false
//                }
//                true
//            },
    ) {
        for (markerItem in markerItems) {
            val markerReg = LatLng(markerItem.latitude, markerItem.longitude)
            Marker(state = MarkerState(position = markerReg),
                icon = BitmapFromVector(LocalContext.current, R.drawable.map_marker),
                onClick = {
//                    state.bottomSheetState.show()
                    state.bottomSheetState.isVisible
                    viewModel.viewState = 1
                    state.bottomSheetState.isVisible
                })
        }
    }
}
/*
IconButton(modifier = Modifier.size(30.dp), onClick = {
    cameraPositionState.move((CameraUpdateFactory.newCameraPosition(cameraPositionState.position)))
}) {
    Icon(painter = painterResource(R.drawable.map_location), contentDescription = null)
}
*/


@Composable
fun Dialog() {
    AlertDialog(onDismissRequest = {}, confirmButton = {
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
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
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
            if (viewModel.time.isNotEmpty() && viewModel.date.isNotEmpty() && viewModel.content.isNotEmpty()) {
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
//

                Log.d("AppointmentReservation", "예약 실패 !")
            }
        }
    }
}
