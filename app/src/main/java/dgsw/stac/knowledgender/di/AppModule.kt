package dgsw.stac.knowledgender.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dgsw.stac.knowledgender.pref.Pref
import dgsw.stac.knowledgender.pref.PrefImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context : Context):DataStore<Preferences>{
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler {
                emptyPreferences()
            }, produceFile = {context.preferencesDataStoreFile("knowledgender")}
        )
    }

    @Provides
    @Singleton
    fun provideUserPref(dataStore: DataStore<Preferences>): Pref = PrefImpl(dataStore)
}