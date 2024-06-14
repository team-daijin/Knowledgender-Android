package dgsw.stac.knowledgender.ui.feature.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import site.algosipeosseong.model.Banner
import site.algosipeosseong.model.Cardnews
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _bannerData = MutableStateFlow<List<Banner>?>(emptyList())
    val bannerData: StateFlow<List<Banner>?> = _bannerData


    private val _cards = MutableStateFlow<List<Cardnews>?>(null)
    val cards = _cards.asStateFlow()


    var cardNewsAvailable = mutableStateOf(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getBannerData()
            getCardnews()
        }
        cardNewsAvailable.value = true
    }

    suspend fun getBannerData() {
        kotlin.runCatching {
            RetrofitBuilder.apiService.banner()
        }.onSuccess {
            _bannerData.value = it
        }
    }

    private suspend fun getCardnews() {
        kotlin.runCatching {
            RetrofitBuilder.apiService.getCardnews()
        }.map {
            _cards.value = it
        }.onFailure {
            Log.d("CardCategory", "카드 카테고리로 불러오기 실패")
        }
    }
}
