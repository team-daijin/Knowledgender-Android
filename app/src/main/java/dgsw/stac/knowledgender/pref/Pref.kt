package dgsw.stac.knowledgender.pref

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

val ACCESSTOKEN_KEY = stringPreferencesKey("accessToken")
val REFRESHTOKEN_KEY = stringPreferencesKey("refreshToken")

val USERNAME = stringPreferencesKey("userName")
val USERAGE = intPreferencesKey("userAge")
val USERGENDER = stringPreferencesKey("userGender")


interface Pref {
    fun getAccessToken(): Flow<String>
    fun getRefreshToken(): Flow<String>

    fun getUserName(): Flow<String>
    fun getUserAge(): Flow<Int>
    fun getUserGender(): Flow<String>



    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken( refreshToken: String)

    suspend fun saveUserName(name: String)

    suspend fun saveUserAge(age: Int)

    suspend fun saveUserGender(gender: String)

}