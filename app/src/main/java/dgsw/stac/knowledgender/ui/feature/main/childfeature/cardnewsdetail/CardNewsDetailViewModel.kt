package dgsw.stac.knowledgender.ui.feature.main.childfeature.cardnewsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.CardResponse
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardNewsDetailViewModel @Inject constructor() : ViewModel() {


    private val _cardNewsDetail = MutableStateFlow<CardResponse?>(null)
    val cardNewsDetail: StateFlow<CardResponse?> = _cardNewsDetail.asStateFlow()

    fun getDetailInfo(id: String) = viewModelScope.launch {
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

