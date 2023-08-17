package dgsw.stac.knowledgender.ui.feature.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dgsw.stac.knowledgender.remote.BannerResponse
import dgsw.stac.knowledgender.remote.CardNewsDetailResponse
import dgsw.stac.knowledgender.remote.Category
import dgsw.stac.knowledgender.remote.RetrofitBuilder
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {


}