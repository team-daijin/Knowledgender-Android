package dgsw.stac.knowledgender.feature.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dgsw.stac.knowledgender.remote.Register
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LoginViewModel : ViewModel() {
    val id = mutableStateOf("")
    val pw = mutableStateOf("")

    var idError = false
    var pwError = false

    private val username = flow {
//        emit(RetrofitBuilder.retrofitService.fetchUsername())
        emit(runCatching {
            UserResponse("A", 1, 2)
        }.map {
            it.username
        }.getOrNull())
    }.stateIn(viewModelScope, SharingStarted.Lazily, "")

    data class UserResponse(
        val username: String,
        val age: Int,
        val something: Any?
    )

    init {
        username.value
        val result = try {
            1
        } catch (e: Exception) {
            2
        }

        val username = kotlin.runCatching {
            UserResponse("A", 1, 2)
        }.map {
            it.username
        }.getOrNull()


        val result2 = runCatching {
            throw Exception()
            1
        }.getOrElse {
//            API
            3
        }

        result2
    }

    suspend fun registerPOST2(userInfo: Register) {
        kotlin.runCatching {
            RetrofitBuilder.retrofitService.register2(userInfo)
        }.onSuccess { response ->
            response
        }.onFailure {
            Log.d("euya", "실패")
        }
    }

    fun registerPOST(userInfo: Register) {
        RetrofitBuilder.retrofitService.register(userInfo).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("euya", "실패")
            }

        })
    }
}
