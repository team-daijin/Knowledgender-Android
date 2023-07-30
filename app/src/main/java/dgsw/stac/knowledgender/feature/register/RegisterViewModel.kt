package dgsw.stac.knowledgender.feature.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {
    val id = mutableStateOf("")
    val pw = mutableStateOf("")
    val name = mutableStateOf("")
    var idError = false
    var pwError = false
}