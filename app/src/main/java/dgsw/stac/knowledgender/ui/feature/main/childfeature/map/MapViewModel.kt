package dgsw.stac.knowledgender.ui.feature.main.childfeature.map

import android.app.Application
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.model.MarkerItem
import dgsw.stac.knowledgender.pref.Pref
import dgsw.stac.knowledgender.remote.AppointmentReservationRequest
import dgsw.stac.knowledgender.remote.AppointmentResponse
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import dgsw.stac.knowledgender.util.Utility
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val application: Application, private val pref: Pref) :
    AndroidViewModel(application) {
    var viewState = 0
    lateinit var viewData: AppointmentResponse

    var appointmentView by mutableStateOf<List<AppointmentResponse>>(emptyList())
    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var content by mutableStateOf("")
    val dateError = false
    val timeError = false
    val contentError = false


    init {
        viewModelScope.launch {
            gatAppointmentView(Utility.reg)?.let {
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

    suspend fun gatAppointmentView(location: LatLng) = runCatching {
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

}