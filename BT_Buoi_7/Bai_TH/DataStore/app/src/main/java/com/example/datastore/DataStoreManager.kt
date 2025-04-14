package com.example.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {
    companion object {
        val COLOR_KEY = stringPreferencesKey("selected_color")
    }

    val selectedColor: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[COLOR_KEY] }

    suspend fun saveColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[COLOR_KEY] = color
        }
    }
}

