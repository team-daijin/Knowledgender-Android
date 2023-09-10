package dgsw.stac.knowledgender.ui.feature.main.childfeature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.pref.Pref
import dgsw.stac.knowledgender.remote.Profile
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val pref: Pref) : ViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile

    suspend fun getProfile() {
        kotlin.runCatching {
            RetrofitBuilder.apiService.getUserInfo("Bearer ${pref.getAccessToken().first()}")
        }.onSuccess {
            _profile.value = it
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.saveToken("","")
        }
    }

    fun userDelete() {
        viewModelScope.launch {
            RetrofitBuilder.apiService.deleteUserInfo("Bearer ${pref.getAccessToken().first()}")
            pref.saveToken("","")
        }
    }
}