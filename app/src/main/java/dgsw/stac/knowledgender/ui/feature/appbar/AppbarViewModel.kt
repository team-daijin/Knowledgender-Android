package dgsw.stac.knowledgender.ui.feature.appbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.pref.Pref
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppbarViewModel @Inject constructor(pref: Pref) : ViewModel() {
    //suspend fun loginCheck(): Boolean = pref.getAccessToken().first().isNotEmpty()

    val isLogin = pref.getAccessToken().map(String::isNotEmpty)
        .stateIn(viewModelScope, SharingStarted.Lazily, false)


    init {
        viewModelScope.launch {
            if (pref.getRefreshToken().first().isNotEmpty()) {
                kotlin.runCatching {
                    RetrofitBuilder.apiService.accessTokenRefresh(pref.getRefreshToken().first())
                }.onSuccess { pref.saveAccessToken(it) }
            }
        }
    }
}