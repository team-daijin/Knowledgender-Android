package dgsw.stac.knowledgender.ui.feature.login

import android.app.Application
import android.content.Intent
import android.util.Log
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val pref: Pref,
    private val application: Application
) : AndroidViewModel(application) {
    var id by mutableStateOf("")
    var pw by mutableStateOf("")

//    val username = MutableStateFlow("")
//    val password = MutableStateFlow("")


    val enabledButton = snapshotFlow { id }.combine(snapshotFlow { pw }) { id, pw ->
        id.isNotEmpty() && pw.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    var idError = false
    var pwError = false

    fun loginPOST(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitBuilder.apiService.login(
                    LoginRequest(
                        accountId = id,
                        password = pw
                    )
                )
            }.onSuccess { response ->
                pref.saveToken(response.accessToken, response.refreshToken)
                onSuccess(Route.MAIN)
                application.startService(
                    Intent(application, PushNotification::class.java).apply {
                        putExtra("Access", pref.getAccessToken().first())
                    }
                )
            }.onFailure {
                Log.d("euya", "로그인 실패")
                idError = true
                pwError = true
            }
        }
    }


}