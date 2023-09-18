package dgsw.stac.knowledgender.ui.feature.main.childfeature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.BannerResponse
import dgsw.stac.knowledgender.remote.Category
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import dgsw.stac.knowledgender.model.CardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _bannerData = MutableStateFlow<List<BannerResponse>?>(emptyList())
    val bannerData: StateFlow<List<BannerResponse>?> = _bannerData

    var cardNewsAvailable = false

    private val _heart = MutableStateFlow<List<CardItem>?>(emptyList())
    val heart: StateFlow<List<CardItem>?> = _heart

    private val _body = MutableStateFlow<List<CardItem>?>(emptyList())
    val body: StateFlow<List<CardItem>?> = _body

    private val _crime = MutableStateFlow<List<CardItem>?>(emptyList())
    val crime: StateFlow<List<CardItem>?> = _crime

    private val _relationship = MutableStateFlow<List<CardItem>?>(emptyList())
    val relationship: StateFlow<List<CardItem>?> = _relationship

    private val _equality = MutableStateFlow<List<CardItem>?>(emptyList())
    val equality: StateFlow<List<CardItem>?> = _equality


    suspend fun getBannerData() {
        kotlin.runCatching {
            RetrofitBuilder.apiService.banner()
        }.onSuccess {
            _bannerData.value = it.bannerResponses
        }
    }

    //    suspend fun gatCardCategory(category: Category): List<CardItem> {
//        val data = mutableListOf<CardItem>()
//        kotlin.runCatching {
//            RetrofitBuilder.retrofitService.cardCategory(category.name)
//        }.onSuccess { response ->
//            for (i in response.indices) {
//                data.add(
//                    CardItem(
//                        response[i].id, response[i].title, response[i].category, response[i].image
//                    )
//                )
//            }
//            Log.d("CardCategory", "카드 카테고리로 불러오기 성공")
//        }.onFailure {
//            // TODO 오류 발생 시 토스트, 스낵바, 알렛 표시
//            Log.d("CardCategory", "카드 카테고리로 불러오기 실패")
//        }
//        return data
//    }
    suspend fun getCardCategory(category: String): List<CardItem> {
        return kotlin.runCatching {
            RetrofitBuilder.apiService.cardCategory(category)
        }.map {
            it.cardResponseList.map { data ->
                cardNewsAvailable = true
                CardItem(data.id, data.title, data.category, data.thumbnail)
            }
        }.onFailure {
            // TODO 오류 발생 시 토스트, 스낵바, 알렛 표시
            Log.d("CardCategory", "카드 카테고리로 불러오기 실패")
        }.getOrDefault(emptyList())
    }

//    suspend fun getBanner(): List<BannerResponse> {
//        var data: List<BannerResponse> = listOf()
//        kotlin.runCatching {
//            RetrofitBuilder.retrofitService.banner()
//        }.onSuccess { response ->
//            data = response
//            Log.d("Banner", "배너 불러오기 성공")
//        }.onFailure {
//            Log.d("Banner", "배너 불러오기 실패")
//        }
//        return data
//    }


}