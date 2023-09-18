package dgsw.stac.knowledgender.ui.feature.login

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.navigation.Route
import dgsw.stac.knowledgender.pref.Pref
import dgsw.stac.knowledgender.remote.LoginRequest
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import dgsw.stac.knowledgender.socket.PushNotification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val pref: Pref,
    private val application: Application
) : AndroidViewModel(application) {
    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    private val _pw = MutableStateFlow("")
    val pw: StateFlow<String> = _pw

    val enabledButton = snapshotFlow { id }.combine(snapshotFlow { pw }) { id, pw ->
        id.value.isNotEmpty() && pw.value.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _errorMSG = MutableStateFlow("")
    val errorMSG: StateFlow<String> = _errorMSG

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error


    fun idChanged(id: String) {
        _id.value = id
    }

    fun pwChanged(pw: String) {
        _pw.value = pw
    }
    fun loginPOST(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitBuilder.apiService.login(
                    LoginRequest(
                        accountId = id.value,
                        password = pw.value
                    )
                )
            }.onSuccess { response ->
                pref.saveAccessToken(response.accessToken)
                pref.saveRefreshToken(response.refreshToken)
                onSuccess(Route.MAIN)
                application.startService(
                    Intent(application, PushNotification::class.java).apply {
                        putExtra("Access", response.accessToken)
                    }
                )
            }.onFailure {
                when(it.message.toString()){
                    "HTTP 500" -> _errorMSG.value = "서버가 원활하지 않습니다.."
                    "HTTP 404" -> _errorMSG.value = "해당 유저를 찾지 못했습니다."
                    "HTTP 400" -> _errorMSG.value = "로그인에 실패했습니다."
                }
                _error.value = true
            }
        }
    }


}