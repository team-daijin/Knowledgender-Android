package dgsw.stac.knowledgender.ui.feature.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dgsw.stac.knowledgender.remote.LoginRequest
import dgsw.stac.knowledgender.remote.RegisterRequest
import dgsw.stac.knowledgender.remote.RetrofitBuilder

class LoginViewModel : ViewModel() {
    val id = mutableStateOf("")
    val pw = mutableStateOf("")

    var idError = false
    var pwError = false

    suspend fun loginPOST(userInfo: LoginRequest) {
        kotlin.runCatching {
            RetrofitBuilder.retrofitService.login(userInfo)
        }.onSuccess { response ->
            response.accessToken
            response.refreshToken
        }.onFailure {
            Log.d("euya", "회원가입 실패")
        }
    }

//    private val username = flow {
////        emit(RetrofitBuilder.retrofitService.fetchUsername())
//        emit(runCatching {
//            UserResponse("A", 1, 2)
//        }.map {
//            it.username
//        }.getOrNull())
//    }.stateIn(viewModelScope, SharingStarted.Lazily, "")
}
