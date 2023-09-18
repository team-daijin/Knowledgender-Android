package dgsw.stac.knowledgender.ui.feature.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.RegisterRequest
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    private val _id = MutableStateFlow("")
    val id: StateFlow<String> = _id

    private val _pw = MutableStateFlow("")
    val pw: StateFlow<String> = _pw

    private val _pwCheck = MutableStateFlow("")
    val pwCheck: StateFlow<String> = _pwCheck

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name


    private val _age = MutableStateFlow(0)
    val age: StateFlow<Int> = _age

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    var idError = mutableStateOf(false)
    var pwError = mutableStateOf(false)
    var pwCheckError = mutableStateOf(false)


    val enabledButton = combine(_id, _pw, _pwCheck, _name, _gender) { id, pw, pwCheck, name, gender ->
        id.isNotBlank() && pw.isNotBlank() && name.matches(Regex("^[가-힣]*\$")) && name.isNotBlank() && gender.isNotBlank() && age.value != 0
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private fun checkInfo(): Boolean {
        if (pwCheck.value != pw.value) {
            pwCheckError.value = true
            return false
        }
        if (pw.value.matches(Regex("/^(?=.*[a-zA-Z])(?=.*[!@#\$%^*+=-])(?=.*[0-9]).{8,30}\$/"))) {
            return true
        } else {
            pwError.value = true
            return false
        }
    }

    fun idChanged(id: String) {
        _id.value = id
    }

    fun pwChanged(pw: String) {
        _pw.value = pw
    }

    fun pwCheckChanged(pwCheck: String) {
        _pwCheck.value = pwCheck
    }

    fun nameChanged(name: String) {
        _name.value = name
    }

    fun ageChanged(age: Int) {
        _age.value = age
    }

    fun genderForMale() {
        _gender.value = "남성"
    }

    fun genderForFemale() {
        _gender.value = "여성"
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