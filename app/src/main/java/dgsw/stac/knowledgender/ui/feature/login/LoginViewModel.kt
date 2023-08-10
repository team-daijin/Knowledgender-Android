package dgsw.stac.knowledgender.ui.feature.login

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.LoginRequest
import dgsw.stac.knowledgender.remote.RegisterRequest
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel : ViewModel() {
    val id = mutableStateOf("")
    val pw = mutableStateOf("")

    var idError = false
    var pwError = false

    fun loginPOST() {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitBuilder.apiService.login(LoginRequest(accountId = id.value, password = pw.value))
            }.onSuccess { response ->
                Log.d("euya", response.accessToken + " 토큰!")
                response.accessToken
                response.refreshToken
            }.onFailure {
                Log.d("euya", "로그인 실패")
            }
        }
    }

    suspend fun saveToken(){

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
