package dgsw.stac.knowledgender.ui.feature.register

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

    val idError = mutableStateOf(false)
    val pwError = mutableStateOf(false)
    val pwCheckError = mutableStateOf(false)

    private val _errorMsg = MutableStateFlow("영문, 숫자 포함 30자리 미만으로 입력해주세요")
    val errorMsg: StateFlow<String> = _errorMsg

    val enabledButton = combine(id, pw, age, name, gender) { id, pw, age, name, gender ->
        id.isNotBlank() && pw.isNotBlank() && name matches Regex("^[가-힣]*$") && name.isNotBlank() && gender.isNotBlank() && age != 0
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)


    private fun validPwCheck(): Boolean {
        return if (pwCheck.value != pw.value) {
            pwCheckError.value = true
            false
        } else {
            pwCheckError.value = false
            true
        }
    }

    private fun validPw(): Boolean {
        val regex = """^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$""".toRegex()
        return if (regex.matches(pw.value)) {
            pwError.value = false
            true
        } else {
            pwError.value = true
            false
        }
    }

    private fun validId(): Boolean {
        val regex = """^(?=.*[A-Za-z0-9])[a-z0-9]*$""".toRegex()
        return if(regex.matches(id.value)) {
            idError.value = false
            true
        } else {
            idError.value = true
            _errorMsg.value = "영문, 숫자 포함 30자리 미만으로 입력해주세요"
            false
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
        if (validId() && validPw() && validPwCheck()) {
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
                }.onSuccess {
                    onSuccess.invoke()
                }.onFailure {
                    idError.value = true
                    _errorMsg.value = "이미 존재하는 아이디입니다."
                }
            }
        }
    }
}