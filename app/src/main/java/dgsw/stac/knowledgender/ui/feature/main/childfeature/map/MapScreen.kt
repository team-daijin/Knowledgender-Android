package dgsw.stac.knowledgender.ui.feature.main.childfeature.map


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.ui.components.MapItemList
import dgsw.stac.knowledgender.ui.components.TextFieldSet
import dgsw.stac.knowledgender.ui.feature.main.CENTER
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkBlack
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.checkAndRequestPermissions
import dgsw.stac.knowledgender.util.dpToSp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val showDialog = mutableStateOf(false)


@Composable
fun MapScreen(
    viewModel: MapViewModel,
    onNavigationRequested: (String) -> Unit
) {


    val isLocationPermitted = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsMap ->
            isLocationPermitted.value = permissionsMap.values.reduce { acc, next -> acc && next }
        }


    LaunchedEffect(key1 = Unit) {
        checkAndRequestPermissions(context, permissions, permissionLauncher)
    }

    val viewState by viewModel.viewState.collectAsState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                        .wrapContentHeight()
                ) {
                    when (viewState) {
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
            if (permissions.all {
                    ContextCompat.checkSelfPermission(
                        context,
                        it
                    ) == PackageManager.PERMISSION_GRANTED
                }) {

                Scaffold { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        Body(
                            viewModel,
                            context,
                            permissions
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .width(100.dp)
                            .wrapContentHeight(),
                        painter = painterResource(id = R.drawable.locationslash),
                        contentDescription = "noLocationPermission",
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "위치 권한이 필요합니다.", color = DarkestBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = dpToSp(
                                dp = 16.dp
                            )
                        )
                    )
                    Button(
                        onClick = { onNavigationRequested(CENTER) },
                        colors = ButtonDefaults.buttonColors(BasePurple),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "새로고침", color = Color.White, style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = dpToSp(
                                    dp = 16.dp
                                )
                            )
                        )
                    }
                }
            }
        }
    }

}


@Composable
@ExperimentalMaterial3Api
fun Body(
    viewModel: MapViewModel,
    context: Context,
    permissions: Array<String>
) {

    val reg by viewModel.reg.collectAsState()
    val appointment by viewModel.appointmentView.collectAsState()
    viewModel.getUserLocation(context = context, permissions = permissions)
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false
            )
        )
    }
    val mapProperties = MapProperties(
        isMyLocationEnabled = true,
        mapType = MapType.NORMAL,
        maxZoomPreference = 30f,
        minZoomPreference = 1f
    )


//    val pos = remember(reg.latitude, reg.longitude) { LatLng(reg.latitude, reg.longitude) }
//    val cameraPositionState = rememberSaveable(pos, saver = CameraPositionState.Saver) {
//        CameraPositionState(
//            position = CameraPosition.fromLatLngZoom(pos, 17f),
//        )
//    }


    if (reg.latitude != 0.0 && reg.longitude != 0.0) {
        val cameraPositionState = rememberCameraPositionState() {
            position = CameraPosition.fromLatLngZoom(reg, 13f)
        }

        viewModel.stopLocationUpdate()
        viewModel.getMarkerItem(reg)
        GoogleMap(
            properties = mapProperties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            appointment?.forEach { appointment ->
                Log.d("domom", appointment.latitude.toString())
                Log.d("domom", appointment.longitude.toString())
                MarkerInfoWindow(
                    title = appointment.name,
                    state = rememberMarkerState(
                        position = LatLng(
                            appointment.latitude,
                            appointment.longitude,

                            )
                    ),
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.mapmarker),
                    onClick = {
                        viewModel.onIconClicked(appointment)
                        false
                    }
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(
                text = "최초 로딩은 조금 오래 걸립니다.", color = DarkestBlack, style = TextStyle(
                    fontFamily = pretendard, fontWeight = FontWeight.Normal, fontSize = dpToSp(
                        dp = 16.dp
                    )
                )
            )
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
    val viewData by viewModel.viewData.collectAsState()
    viewData?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            Image(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable { viewModel.onBackClicked() },
                painter = painterResource(id = R.drawable.arrowbackward),
                contentDescription = "arrowBackward"
            )
            Row(
                modifier = Modifier
                    .height(190.dp)
                    .fillMaxWidth(),
            ) {

                AsyncImage(
                    model = it.image,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .size(120.dp),
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(
                        text = it.name, color = DarkBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            fontSize = dpToSp(dp = 20.dp)
                        )
                    )
                    Text(
                        text = it.address, color = BaseBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = dpToSp(dp = 12.dp)
                        )
                    )
                    Text(
                        text = it.contact, color = BaseBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = dpToSp(dp = 12.dp)
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = it.introduce, color = DarkBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = dpToSp(dp = 12.dp)
                        )
                    )
                }


            }
        }
    }
}

@Composable
fun BottomSheetReservationScreen(viewModel: MapViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val viewData by viewModel.viewData.collectAsState()
    if (showDialog.value) {
        Dialog()
    }
    viewData?.let {
        Column {
            Row(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {

                AsyncImage(
                    model = it.image,
                    contentDescription = "Center Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )

                Column {
                    Text(
                        text = it.name, color = DarkBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                    ) // 이름
                    Text(
                        text = it.contact, color = BaseBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    ) // 전번
                    Text(
                        text = it.address, color = BaseBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    ) // 주소
                    Text(
                        text = it.introduce, color = DarkBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    ) // 설명
                    Button(onClick = { }) {
                        Text(
                            text = "예약 날짜", color = LightBlack, style = TextStyle(
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Normal,
                                fontSize = dpToSp(
                                    dp = 16.dp
                                )
                            )
                        )
                    }
                    Button(onClick = { /*TODO*/ }) {

                    }
                    TextFieldSet(textContent = "예약내용",
                        textFieldPlaceHolder = "예약내용",
                        errorMsg = "예약내용을 입력해주세요.",
                        value = viewModel.content,
                        onValueChange = {
                            viewModel.content = it
                        })
                    if (viewModel.time.isNotEmpty() && viewModel.date.isNotEmpty() && viewModel.content.isNotEmpty()) {
                        Button(
                            modifier = Modifier
                                .height(45.dp)
                                .width(330.dp),
                            onClick = {
                                coroutineScope.launch {
                                    withContext(Dispatchers.IO) {
                                        try {
                                            viewModel.postReservation(it.id)
                                        } catch (e: Exception) {
                                            Log.d("TAG", "${e.message}")
                                        }
                                    }
                                }
                                showDialog.value = true
                            },
                            colors = ButtonDefaults.buttonColors(BasePurple)
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
                        Log.d("AppointmentReservation", "예약 실패 !")
                    }
                }
            }
        }
    }

}
