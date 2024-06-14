package dgsw.stac.knowledgender.ui.feature.map


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.navigation.Route.CENTER
import dgsw.stac.knowledgender.ui.components.MapItemList
import dgsw.stac.knowledgender.ui.components.TextFieldSet
import dgsw.stac.knowledgender.ui.theme.BaseBlack
import dgsw.stac.knowledgender.ui.theme.BasePurple
import dgsw.stac.knowledgender.ui.theme.DarkBlack
import dgsw.stac.knowledgender.ui.theme.DarkestBlack
import dgsw.stac.knowledgender.ui.theme.LightBlack
import dgsw.stac.knowledgender.ui.theme.LighterBlack
import dgsw.stac.knowledgender.ui.theme.LighterSky
import dgsw.stac.knowledgender.ui.theme.pretendard
import dgsw.stac.knowledgender.util.checkAndRequestPermissions
import dgsw.stac.knowledgender.util.convertMillisToDate
import dgsw.stac.knowledgender.util.dpToSp

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
    val sheetController = rememberBottomSheetScaffoldState(
        SheetState(
            initialValue = SheetValue.Expanded,
            skipHiddenState = true,
            skipPartiallyExpanded = false
        )
    )

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BottomSheetScaffold(
            scaffoldState = sheetController,
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
                zoomControlsEnabled = false,
                mapToolbarEnabled = false
            )
        )
    }
    val mapProperties = MapProperties(
        isMyLocationEnabled = true,
        mapType = MapType.NORMAL,
        maxZoomPreference = 30f,
        minZoomPreference = 13f
    )


//    val pos = remember(reg.latitude, reg.longitude) { LatLng(reg.latitude, reg.longitude) }
//    val cameraPositionState = rememberSaveable(pos, saver = CameraPositionState.Saver) {
//        CameraPositionState(
//            position = CameraPosition.fromLatLngZoom(pos, 17f),
//        )
//    }


    if (reg.latitude != 0.0 && reg.longitude != 0.0) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(reg, 13f)
        }
        if (cameraPositionState.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE) {
            viewModel.onBackClicked()
        }
        viewModel.getMarkerItem(reg)
        GoogleMap(
            properties = mapProperties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            appointment?.forEach { appointment ->
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
                text = "잠시만 기다려주세요.", color = DarkestBlack, style = TextStyle(
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
fun BottomSheetReservationScreen(viewModel: MapViewModel) {
    val viewData by viewModel.viewData.collectAsState()

    val content by viewModel.content.collectAsState()


    val enabledButton by viewModel.enabledButton.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val openDatePicker = remember { mutableStateOf(false) }
    val openTimePicker = remember { mutableStateOf(false) }
    var selectedHour by remember {
        mutableIntStateOf(0) // or use  mutableStateOf(0)
    }

    var selectedMinute by remember {
        mutableIntStateOf(0) // or use  mutableStateOf(0)
    }

    var showDialog = remember {
        mutableStateOf(false)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute
    )
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis > System.currentTimeMillis()
            }
        })
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    }
    LaunchedEffect(key1 = selectedDate) {
        selectedDate?.let {
            viewModel.onUpdateDate(it)
        }
    }
    LaunchedEffect(key1 = selectedHour) {
        viewModel.onUpdateTime(selectedHour,selectedMinute)
    }
    if (openDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDatePicker.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDatePicker.value = false
                    }
                ) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDatePicker.value = false
                    }
                ) {
                    Text("취소")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
    if (openTimePicker.value) {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            onDismissRequest = { openTimePicker.value = false }
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = Color.LightGray.copy(alpha = 0.3f)
                    )
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // time picker
                TimePicker(state = timePickerState)

                // buttons
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // dismiss button
                    TextButton(onClick = { openTimePicker.value = false }) {
                        Text(text = "Dismiss")
                    }

                    // confirm button
                    TextButton(
                        onClick = {
                            openTimePicker.value = false
                            selectedHour = timePickerState.hour
                            selectedMinute = timePickerState.minute
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
    if (showDialog.value) {
        Dialog()
    }
    viewData?.let { appointment ->
        Column(verticalArrangement = Arrangement.Bottom) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp)
            ) {

                AsyncImage(
                    model = appointment.image,
                    contentDescription = "Center Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column {
                    Text(
                        text = appointment.name, color = DarkBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                    ) // 이름
                    Text(
                        text = appointment.contact, color = BaseBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    ) // 전번
                    Text(
                        text = appointment.address, color = BaseBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    ) // 주소
                    Text(
                        text = appointment.description, color = DarkBlack, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.clickable { openDatePicker.value = true },
                            painter = painterResource(id = R.drawable.calender),
                            contentDescription = "datepicker"
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "예약 날짜", color = DarkestBlack, style = TextStyle(
                                    fontFamily = pretendard,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = dpToSp(
                                        dp = 13.dp
                                    )
                                )
                            )
                            Text(
                                text = selectedDate?.let { it } ?: kotlin.run { "yyyy/mm/dd" },
                                color = LightBlack,
                                style = TextStyle(
                                    fontFamily = pretendard,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = dpToSp(
                                        dp = 16.dp
                                    )
                                )
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.clickable {
                                openTimePicker.value = true
                            },
                            painter = painterResource(id = R.drawable.clock),
                            contentDescription = "datepicker"
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "예약 시간", color = DarkestBlack, style = TextStyle(
                                    fontFamily = pretendard,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = dpToSp(
                                        dp = 13.dp
                                    )
                                )
                            )
                            Text(
                                text = if (selectedHour != 0 && selectedMinute != 0) {
                                    "$selectedHour:$selectedMinute"
                                } else {
                                       "HH/MM"
                                }, color = LightBlack, style = TextStyle(
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
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "예약 내용",
                        style = TextStyle(
                            fontFamily = pretendard,
                            fontSize = dpToSp(13.dp),
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        modifier = Modifier
                            .heightIn(44.dp)
                            .fillMaxWidth(),
                        value = content,
                        interactionSource = interactionSource,
                        onValueChange = {
                            viewModel.onUpdateContent(it)
                        },
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .heightIn(44.dp)
                                    .border(
                                        1.dp,
                                        color = if (isFocused) BasePurple else LighterSky,
                                        RoundedCornerShape(8.dp)
                                    ),
                                Alignment.CenterStart,
                            ) {
                                Box(modifier = Modifier.padding(start = 16.dp)) {
                                    if (content.isEmpty()) {
                                        Text(
                                            "예약내용을 입력해주세요.",
                                            color = LighterBlack,
                                            style = TextStyle(
                                                fontSize = 16.sp,
                                                fontFamily = pretendard,
                                                fontWeight = FontWeight.Normal
                                            ),
                                            modifier = Modifier
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .height(45.dp)
                        .width(330.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    onClick = {
                        if (enabledButton) {
                            viewModel.postReservation()
                            showDialog.value = true
                        }
                    },
                    colors = if (enabledButton) {
                        ButtonDefaults.buttonColors(BasePurple)
                    } else {
                        ButtonDefaults.buttonColors(LighterBlack)
                    }
                ) {
                    Text(
                        text = "예약하기", color = Color.White, style = TextStyle(
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp
                        )
                    )
                }
            }
        }
    }
}
