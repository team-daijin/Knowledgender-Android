package dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.CardCategoryResponse
import dgsw.stac.knowledgender.remote.CardNewsDetailResponse
import dgsw.stac.knowledgender.remote.Category
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import dgsw.stac.knowledgender.util.BODY
import dgsw.stac.knowledgender.util.CRIME
import dgsw.stac.knowledgender.util.HEART
import dgsw.stac.knowledgender.util.RELATIONSHIP
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardNewsViewModel @Inject constructor() : ViewModel() {
    private val _cardNews: MutableStateFlow<List<CardCategoryResponse>?> = MutableStateFlow(emptyList())
    val cardNews: StateFlow<List<CardCategoryResponse>?> = _cardNews.asStateFlow()

    fun getCardNewsByCategory(category: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitBuilder.apiService.cardCategory(category)
            }.onSuccess { data ->
                _cardNews.value = data
            }.onFailure {
                it
            }
        }
    }
    fun getTitle(category: String): String {
        return when (category) {
            HEART -> "마음 상담소로 오세요"
            BODY -> "나만 몰랐던 나의 몸"
            CRIME -> "나를 확실하게 지키는 법"
            RELATIONSHIP -> "너와 나의 연결고리"
            else -> "세상에 같은 사람은 없다"
        }
    }
    fun getDescription(category: String): String {
        return when (category) {
            HEART -> "내 안에 숨어있는 마음상담소로 초대합니다!"
            BODY -> "나조차도 모르고 있었던 나의 몸 속 비밀"
            CRIME -> "폭력으로부터 나를 올바른 방법으로 보호해봅시다"
            RELATIONSHIP -> "즐겁고 행복한 우리의 관계를 건강하게 유지하는 법"
            else -> "차이는 틀린 것이 아닌 다른 것!"
        }
    }
}