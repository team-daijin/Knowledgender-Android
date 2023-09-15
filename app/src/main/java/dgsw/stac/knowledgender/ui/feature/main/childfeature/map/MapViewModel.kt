package dgsw.stac.knowledgender.ui.feature.main.childfeature.map

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.model.MarkerItem
import dgsw.stac.knowledgender.pref.Pref
import dgsw.stac.knowledgender.remote.AppointmentReservationRequest
import dgsw.stac.knowledgender.remote.AppointmentResponse
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val application: Application,
    private val pref: Pref
) :
    AndroidViewModel(application) {

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    var mLastLocation: Location = Location("point A")
    internal lateinit var mLocationRequest: LocationRequest

    var viewState = 0
    lateinit var viewData: AppointmentResponse

    var appointmentView by mutableStateOf<List<AppointmentResponse>>(emptyList())
    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var content by mutableStateOf("")
    val dateError = false
    val timeError = false
    val contentError = false
    val _reg = MutableStateFlow<LatLng>(LatLng(0.0, 0.0))
    val reg: StateFlow<LatLng> = _reg

    init {
        viewModelScope.launch {
            getAppointmentView(_reg.value)?.let {
                appointmentView = it
            } ?: run {
                // TODO 실패시 할 거
            }
        }
    }

    suspend fun postReservation(clientId: String) {
        kotlin.runCatching {
            RetrofitBuilder.apiService.getReservation(
                pref.getAccessToken().first(),
                AppointmentReservationRequest(
                    date = date,
                    time = time,
                    content = content,
                    clientId = clientId
                )
            )
        }
    }

    suspend fun getMarkerItem(location: LatLng): List<MarkerItem> {
        val datas = mutableListOf<MarkerItem>()
        kotlin.runCatching {
            RetrofitBuilder.apiService.appointmentView(location.latitude, location.longitude)
        }.map {
            it.map {
                val location = geoCoding(it.address)
                datas.add(MarkerItem(location.latitude, location.longitude))
            }
        }.onFailure {
            // TODO 실패시 할 거
            Log.d("MarkerItem", "위치 불러오기 실패")
        }
        return datas
    }

    suspend fun getAppointmentView(location: LatLng) = runCatching {
        RetrofitBuilder.apiService.appointmentView(location.latitude, location.longitude)
    }.getOrNull()

    fun geoCoding(address: String): Location {
        return try {
            Geocoder(application, Locale.KOREA).getFromLocationName(address, 1)?.let {
                Location("").apply {
                    latitude = it[0].latitude
                    longitude = it[0].longitude
                }
            } ?: Location("").apply {
                latitude = 0.0
                longitude = 0.0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            geoCoding(address) //재시도
        }
    }

    fun onColumnItemClicked(data: AppointmentResponse) {
        viewState = 2
        viewData = data
    }

    fun startLocationUpdates() {

        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        if (ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return
        }
        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )

    }

    // 위치 콜백 멈춤 !
    fun removeLocationUpdate() {
        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            mLastLocation = locationResult.lastLocation!!
            _reg.value = LatLng(mLastLocation.latitude, mLastLocation.longitude)
            Log.d("Location", reg.toString())
        }
    }

}