package dgsw.stac.knowledgender.ui.feature.cardnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.CardResponse
import dgsw.stac.knowledgender.remote.CardResponseList
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import dgsw.stac.knowledgender.util.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import site.algosipeosseong.model.CardnewsCategory
import javax.inject.Inject

@HiltViewModel
class CardNewsViewModel @Inject constructor() : ViewModel() {
    private val _cardNews: MutableStateFlow<List<CardnewsCategory>?> = MutableStateFlow(emptyList())
    val cardNews = _cardNews.asStateFlow()

    fun getCardNewsByCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                RetrofitBuilder.apiService.getCardnewsByCategory(category.str)
            }.onSuccess { data ->
                _cardNews.value = data
            }.onFailure {

            }
        }
    }
    fun getTitle(category: String): String {
        val gettitleByCategory = mapOf(
            Pair(Category.HEART.str,"마음 상담소로 오세요"),
            Pair(Category.BODY.str,"나만 몰랐던 나의 몸"),
            Pair(Category.RELATION.str,"너와 나의 연결고리"),
            Pair(Category.VIOLENCE.str,"나를 확실하게 지키는 법"),
            Pair(Category.EQUALITY.str,"세상에 같은 사람은 없다")
        )
        return gettitleByCategory[category]!!
    }

    fun getSubTitle(category: String): String {
        val gettitleByCategory = mapOf(
            Pair(Category.HEART.str,"내 안에 숨어있는 마음상담소로 초대합니다!"),
        Pair(Category.BODY.str,"나조차도 모르고 있었던 나의 몸 속 비밀"),
        Pair(Category.RELATION.str,"즐겁고 행복한 우리의 관계를 건강하게 유지하는 법"),
        Pair(Category.VIOLENCE.str,"폭력으로부터 나를 올바른 방법으로 보호해봅시다"),
        Pair(Category.EQUALITY.str,"차이는 틀린 것이 아닌 다른 것!")
        )
        return gettitleByCategory[category]!!
    }
}