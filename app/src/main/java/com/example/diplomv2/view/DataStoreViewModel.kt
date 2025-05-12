package com.example.diplomv2.view

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DataStoreViewModel: ViewModel() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "nav")
    val EXAMPLE_COUNTER = booleanPreferencesKey("example_counter")

    fun readExampleCounter(context: Context): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[EXAMPLE_COUNTER] ?: false
            }
    }

    fun writeExampleCounterTrue(context: Context, navBoolean: Boolean) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[EXAMPLE_COUNTER] = navBoolean
            }
        }

    }
}