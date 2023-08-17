package dgsw.stac.knowledgender.pref

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

val ACCESSTOKEN_KEY = stringPreferencesKey("accessToken")
val REFRESHTOKEN_KEY = stringPreferencesKey("refreshToken")

interface Pref{

    fun getAccessToken(): Flow<String>


    suspend fun saveToken(accessToken:String, refreshToken: String)

}