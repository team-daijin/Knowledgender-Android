package dgsw.stac.knowledgender.ui.feature.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.RegisterRequest
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    var id = MutableStateFlow("")
    var pw = MutableStateFlow("")
    var name = MutableStateFlow("")
    var age = MutableStateFlow(0)
    var gender = MutableStateFlow("")
    var pwCheck = MutableStateFlow("")
    var idError = mutableStateOf(false)
    var pwError = mutableStateOf(false)
    var pwCheckError = mutableStateOf(false)


    val enabledButton = combine(id, pw, pwCheck, name, gender) { id, pw, pwCheck, name, gender ->
        id.isNotBlank() && pw.isNotBlank() && name.matches(Regex("^[가-힣]*\$")) && name.isNotBlank() && gender.isNotBlank() && age.value != 0
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private fun checkInfo(): Boolean {
        if (!(pw.value.matches(Regex("^.*(?=^.{8}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$")))) {
            pwError.value = true
            return false
        }
        if (pwCheck.value != pw.value) {
            pwCheckError.value = true
            return false
        }
        return true
    }

    fun registerProcess(onSuccess: () -> Unit) {
        if (checkInfo()) {
            viewModelScope.launch {
                kotlin.runCatching {
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
                }.onSuccess { onSuccess.invoke() }.onFailure { idError.value = true }
            }
        }
    }
//    private val username = flow {
//        emit(RetrofitBuilder.retrofitService.fetchUsername())
//    }.stateIn(viewModelScope, SharingStarted.Lazily, "")
}