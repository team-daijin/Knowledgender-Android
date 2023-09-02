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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val _bannerData = MutableStateFlow<List<BannerResponse>?>(null)
    val bannerData = _bannerData

    var cardNewsAvailable = false

    init {

    }

    suspend fun getBannerData(){
        viewModelScope.launch {
            getBanner()?.let {
                Log.d("euya", "있음")
                _bannerData.value = it
            } ?: run {
                Log.d("euya", "없음")
            }
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
    suspend fun getCardCategory(category: Category): List<CardItem> {
        return kotlin.runCatching {
            RetrofitBuilder.apiService.cardCategory(category.name)
        }.map {
            it.map { data ->
                cardNewsAvailable = true
                CardItem(data.id, data.title, data.category, data.image)
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

    suspend fun getBanner() = runCatching {
        RetrofitBuilder.apiService.banner()
    }.getOrNull()
}