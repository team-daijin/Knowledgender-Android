package dgsw.stac.knowledgender.ui.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.pref.Pref
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pref: Pref) : ViewModel() {
    //suspend fun loginCheck(): Boolean = pref.getAccessToken().first().isNotEmpty()

    val isLogin = pref.getAccessToken().map(String::isNotEmpty)
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun loginCheck(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(pref.getAccessToken().first().isNotEmpty())
        }
    }

    init {
//        viewModelScope.launch{ pref.saveToken("SEX", "PANGM") }
    }
}