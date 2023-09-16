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

    override fun getRefreshToken(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[REFRESHTOKEN_KEY] ?: ""
        }
    }

    override fun getUserName(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[USERNAME] ?: ""
        }
    }

    override fun getUserAge(): Flow<Int> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[USERAGE] ?: 0
        }
    }

    override fun getUserGender(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map {
            it[USERGENDER] ?: ""
        }
    }


    override suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit {
            it[REFRESHTOKEN_KEY] = refreshToken
        }
    }


    override suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit {
            it[ACCESSTOKEN_KEY] = accessToken
        }
    }

    override suspend fun saveUserName(name: String) {
        dataStore.edit {
            it[USERNAME] = name
        }
    }

    override suspend fun saveUserAge(age: Int) {
        dataStore.edit {
            it[USERAGE] = age
        }
    }

    override suspend fun saveUserGender(gender: String) {
        dataStore.edit {
            it[USERGENDER] = gender
        }
    }

}