package dgsw.stac.knowledgender.ui.feature.cardnewsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.CardResponse
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import site.algosipeosseong.model.CardnewsDetail
import javax.inject.Inject

@HiltViewModel
class CardNewsDetailViewModel @Inject constructor() : ViewModel() {


    private val _cardNewsDetail = MutableStateFlow<CardnewsDetail?>(null)
    val cardNewsDetail = _cardNewsDetail.asStateFlow()

    fun getDetailInfo(id: String) = viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {
            RetrofitBuilder.apiService.getCardNewsDetail(id)
        }.onSuccess { _cardNewsDetail.value = it }
    }


//    fun getDetailInfo(id: String) = flow {
//        emit(RetrofitBuilder.apiService.getCardNewsDetail(id))
//    }.catch {
//            // TODO ERROR HANDLEING
//            null
//    }.stateIn(viewModelScope, SharingStarted.Lazily, null)
}

