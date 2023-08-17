package dgsw.stac.knowledgender.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class PrefImpl(private val dataStore: DataStore<Preferences>): Pref {
    override fun getAccessToken(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[ACCESSTOKEN_KEY] ?: ""
        }
    }

    override suspend fun saveToken(accessToken: String, refreshToken: String) {
        dataStore.edit {
            it[ACCESSTOKEN_KEY] = accessToken
            it[REFRESHTOKEN_KEY] = refreshToken
        }
    }

}