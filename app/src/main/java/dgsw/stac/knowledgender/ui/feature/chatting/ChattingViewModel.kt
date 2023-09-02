package dgsw.stac.knowledgender.ui.feature.chatting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(): ViewModel() {

    val message = MutableStateFlow("")

    fun sendMessage() {


    }
}