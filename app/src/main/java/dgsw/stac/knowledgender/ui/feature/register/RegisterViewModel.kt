package dgsw.stac.knowledgender.ui.feature.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.RegisterRequest
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class RegisterViewModel : ViewModel() {
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


    fun registerPOST() {
        viewModelScope.launch {
            RetrofitBuilder.apiService.register(
                RegisterRequest(
                    accountId = id.value,
                    password = pw.value,
                    name = name.value,
                    age = age.value,
                    gender = when (gender.value) {
                        "남성" -> "MALE"
                        else -> "FEMALE"
                    }
                )
            )
        }

    }
}