package dgsw.stac.knowledgender.ui.feature.login

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.R
import dgsw.stac.knowledgender.navigation.Route
import dgsw.stac.knowledgender.pref.Pref
import dgsw.stac.knowledgender.remote.LoginRequest
import dgsw.stac.knowledgender.remote.RegisterRequest
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import dgsw.stac.knowledgender.socket.PushNotification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

    val enabledButton =  combine(id,pw) { id, pw ->
        id.isNotEmpty() && pw.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _errorMSG = MutableStateFlow("")
    val errorMSG: StateFlow<String> = _errorMSG

    val error = mutableStateOf(false)


    fun idChanged(id: String) {
        _id.value = id
    }

    fun pwChanged(pw: String) {
        _pw.value = pw
    }

    fun loginPOST(onSuccess: (String) -> Unit) {
        runBlocking {  }

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
                onSuccess(Route.HOME)
                application.startService(
                    Intent(application, PushNotification::class.java).apply {
                        putExtra("Access", response.accessToken)
                    }
                )
            }.onFailure {
                _errorMSG.value = when(it.message.toString()){
                    "HTTP 500" -> "서버가 원활하지 않습니다.."
                    "HTTP 404" -> "해당 유저를 찾지 못했습니다."
                    "HTTP 400" -> "로그인에 실패했습니다."
                    else -> ""
                }
                error.value = true
            }
        }
    }

    @Composable
    fun getGoogleClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(stringResource(id = R.string.gci))
            .build()
        return GoogleSignIn.getClient(application,gso)
    }

    fun postIdToken(id: String,pw: String,name: String,age: Int,gender: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitBuilder.apiService.register(RegisterRequest(accountId = id, password = pw,name = name,age = age, gender = gender))
            }.onSuccess {  }
        }
    }
}