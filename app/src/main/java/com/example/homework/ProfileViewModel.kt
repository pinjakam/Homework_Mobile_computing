package com.example.homework

import android.app.Application
import android.net.Uri
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Application.dataStore by preferencesDataStore("profile_prefs")

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val USERNAME = stringPreferencesKey("username")
    private val IMAGE_URI = stringPreferencesKey("image_uri")

    val usernameFlow: Flow<String> =
        application.dataStore.data.map { it[USERNAME] ?: "" }

    val imageUriFlow: Flow<String> =
        application.dataStore.data.map { it[IMAGE_URI] ?: "" }

    fun saveProfile(username: String, imageUri: Uri?) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { prefs ->
                prefs[USERNAME] = username
                prefs[IMAGE_URI] = imageUri?.toString() ?: ""
            }
        }
    }
}
