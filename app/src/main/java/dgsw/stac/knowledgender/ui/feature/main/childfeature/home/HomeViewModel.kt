package dgsw.stac.knowledgender.ui.feature.main.childfeature.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.BannerResponse
import dgsw.stac.knowledgender.remote.CardResponse
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import dgsw.stac.knowledgender.util.BODY
import dgsw.stac.knowledgender.util.CRIME
import dgsw.stac.knowledgender.util.EQUALITY
import dgsw.stac.knowledgender.util.HEART
import dgsw.stac.knowledgender.util.RELATIONSHIP
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _bannerData = MutableStateFlow<List<BannerResponse>?>(emptyList())
    val bannerData: StateFlow<List<BannerResponse>?> = _bannerData


    private val _heart = MutableStateFlow<List<CardResponse>?>(null)
    val heart: StateFlow<List<CardResponse>?> = _heart

    private val _body = MutableStateFlow<List<CardResponse>?>(null)
    val body: StateFlow<List<CardResponse>?> = _body

    private val _crime = MutableStateFlow<List<CardResponse>?>(null)
    val crime: StateFlow<List<CardResponse>?> = _crime

    private val _relationship = MutableStateFlow<List<CardResponse>?>(null)
    val relationship: StateFlow<List<CardResponse>?> = _relationship

    private val _equality = MutableStateFlow<List<CardResponse>?>(null)
    val equality: StateFlow<List<CardResponse>?> = _equality

    var cardNewsAvailable = mutableStateOf(false)

    init {
        viewModelScope.launch {
            getBannerData()
            getCardCategory(HEART)
            getCardCategory(BODY)
            getCardCategory(CRIME)
            getCardCategory(RELATIONSHIP)
            getCardCategory(EQUALITY)
        }
        cardNewsAvailable.value = true
    }

    suspend fun getBannerData() {
        kotlin.runCatching {
            RetrofitBuilder.apiService.banner()
        }.onSuccess {
            _bannerData.value = it.bannerResponses
        }
    }

    private suspend fun getCardCategory(category: String) {
        kotlin.runCatching {
            RetrofitBuilder.apiService.cardCategory(category)
        }.map {
            when (category) {
                HEART -> _heart.value = it.cardResponseList
                BODY -> _body.value = it.cardResponseList
                RELATIONSHIP -> _relationship.value = it.cardResponseList
                CRIME -> _crime.value = it.cardResponseList
                EQUALITY -> _equality.value = it.cardResponseList
            }
        }.onFailure {
            Log.d("CardCategory", "카드 카테고리로 불러오기 실패")
        }
    }
}
