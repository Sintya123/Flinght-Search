package com.example.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.model.FlightSearchDatabase
import com.example.flightsearch.data.model.UserPreferencesRepository

private const val INPUT_PREFERENCE_NAME = "input_string"
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = INPUT_PREFERENCE_NAME
)
class FlightSearchApplication : Application(){
    var userPreferencesRepository: UserPreferencesRepository? = null
    val database: FlightSearchDatabase by lazy { FlightSearchDatabase.getDatabase(this) }
    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}