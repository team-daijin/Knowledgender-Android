package dgsw.stac.knowledgender.ui.feature.main.childfeature.map

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.pref.Pref
import dgsw.stac.knowledgender.remote.AppointmentReservationRequest
import dgsw.stac.knowledgender.remote.AppointmentResponse
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val pref: Pref
) : ViewModel() {

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    var mLastLocation: Location = Location("point")
    internal lateinit var mLocationRequest: LocationRequest


    private val _viewState = MutableStateFlow(0)
    val viewState: StateFlow<Int> = _viewState

    private val _viewData = MutableStateFlow<AppointmentResponse?>(null)
    val viewData: StateFlow<AppointmentResponse?> = _viewData


    private val _appointmentView = MutableStateFlow<List<AppointmentResponse>?>(null)
    val appointmentView: StateFlow<List<AppointmentResponse>?> = _appointmentView

    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var content by mutableStateOf("")

    private val _reg = MutableStateFlow(LatLng(0.0, 0.0))
    val reg: StateFlow<LatLng> = _reg

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

    fun getMarkerItem(location: LatLng) {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitBuilder.apiService.appointmentView(location.latitude, location.longitude)
            }.map {
                _appointmentView.value = it
            }.onFailure {
                Log.d("MarkerItem", "위치 불러오기 실패")
            }
        }
    }


    fun onBackClicked() {
        _viewState.value = 0
    }

    fun onIconClicked(data: AppointmentResponse) {
        _viewState.value = 1
       _viewData.value = data
    }


    fun onColumnItemClicked(data: AppointmentResponse) {
        _viewState.value = 2
        _viewData.value = data
    }


    private lateinit var locationCallback: LocationCallback

    //The main entry point for interacting with the Fused Location Provider
    private lateinit var locationProvider: FusedLocationProviderClient


    @Composable
    fun getUserLocation(context: Context, permissions: Array<String>): LatandLong {

        // The Fused Location Provider provides access to location APIs.
        locationProvider = LocationServices.getFusedLocationProviderClient(context)

        var currentUserLocation by remember { mutableStateOf(LatandLong(0.0, 0.0)) }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {


                for (location in result.locations) {
                    // Update data class with location data
                    _reg.value = LatLng(location.latitude, location.longitude)
                    currentUserLocation = LatandLong(location.latitude, location.longitude)
                }

            }
        }
        if (permissions.all {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
        ) {
            locationUpdate(context, permissions)
        }

        Log.d("dksltlqkf", currentUserLocation.toString())
        return currentUserLocation

    }

    fun stopLocationUpdate() {
        try {
            //Removes all location updates for the given callback.
            val removeTask = locationProvider.removeLocationUpdates(locationCallback)
            removeTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("euya", "Location Callback removed.")
                } else {
                    Log.d("euya", "Failed to remove Location Callback.")
                }
            }
        } catch (se: SecurityException) {
            Log.e("euya", "Failed to remove Location Callback.. $se")
        }
    }


    private fun locationUpdate(context: Context, permissions: Array<String>) {

        locationCallback.let { it ->
            //An encapsulation of various parameters for requesting
            // location through FusedLocationProviderClient.
            val locationRequest: LocationRequest =
                LocationRequest.create().apply {
                    interval = TimeUnit.SECONDS.toMillis(10)
                    fastestInterval = TimeUnit.SECONDS.toMillis(0)
                    maxWaitTime = TimeUnit.SECONDS.toMillis(10)
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
            //use FusedLocationProviderClient to request location update
            if (permissions.all {str ->
                    ContextCompat.checkSelfPermission(
                        context,
                        str
                    ) == PackageManager.PERMISSION_GRANTED
                }
            ) {
                locationProvider.requestLocationUpdates(
                    locationRequest,
                    it,
                    Looper.getMainLooper()
                )
            }
        }
    }

    data class LatandLong(
        val latitude: Double,
        val longitude: Double
    )

}