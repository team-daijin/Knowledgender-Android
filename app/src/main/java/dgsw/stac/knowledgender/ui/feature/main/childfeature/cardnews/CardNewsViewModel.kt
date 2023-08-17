package dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.CardCategoryResponse
import dgsw.stac.knowledgender.remote.CardNewsDetailResponse
import dgsw.stac.knowledgender.remote.Category
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardNewsViewModel @Inject constructor() : ViewModel() {

    private val _cardNews: MutableStateFlow<List<CardCategoryResponse>>? = null
    val cardNews: StateFlow<List<CardCategoryResponse>> = _cardNews!!.asStateFlow()

    fun getCardNewsByCategory(category: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                RetrofitBuilder.apiService.cardCategory(category)
            }.onSuccess { _cardNews?.value = it }
        }
    }
}