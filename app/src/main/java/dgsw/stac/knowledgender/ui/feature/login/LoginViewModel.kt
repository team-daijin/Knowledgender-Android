package dgsw.stac.knowledgender.ui.feature.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val id = mutableStateOf("")
    val pw = mutableStateOf("")

    var idError = false
    var pwError = false

//    private val username = flow {
////        emit(RetrofitBuilder.retrofitService.fetchUsername())
//        emit(runCatching {
//            UserResponse("A", 1, 2)
//        }.map {
//            it.username
//        }.getOrNull())
//    }.stateIn(viewModelScope, SharingStarted.Lazily, "")
}
