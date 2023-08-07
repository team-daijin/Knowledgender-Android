package dgsw.stac.knowledgender.ui.feature.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dgsw.stac.knowledgender.remote.RegisterRequest
import dgsw.stac.knowledgender.remote.RetrofitBuilder

class RegisterViewModel: ViewModel() {
    val id = mutableStateOf("")
    val pw = mutableStateOf("")
    val name = mutableStateOf("")
    val age = mutableStateOf(0)
    val gender = mutableStateOf("")
    val pwCheck = mutableStateOf("")
    var idError = false
    var pwError = false
    var pwCheckError = false

//    fun registerPOST(userInfo: Register) {
//        RetrofitBuilder.retrofitService.register(userInfo).enqueue(object : Callback<Boolean> {
//            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
//
//            }
//
//            override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                Log.d("euya", "실패")
//            }
//
//        })
//    }

    suspend fun registerPOST(userInfo: RegisterRequest) {
        kotlin.runCatching {
            RetrofitBuilder.retrofitService.register(userInfo)
        }.onSuccess { response ->
            Log.d("euya", "회원가입 성공")
        }.onFailure {
            Log.d("euya", "회원가입 실패")
        }
    }
}