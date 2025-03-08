package com.kalagui.residify.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

@Singleton
class AuthDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    // Preference keys
    companion object {
        val TOKEN_KEY = stringPreferencesKey("access_token")
        val USERNAME_KEY = stringPreferencesKey("username")
        val USER_ID_KEY = intPreferencesKey("user_id")
    }

    // Plain DataStore for non-sensitive data
    private val dataStore = context.authDataStore

    // Encrypted file for token (for extra security)
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val tokenFile = File(context.filesDir, "local_data.txt")
    private val encryptedFile = EncryptedFile.Builder(
        tokenFile,
        context,
        masterKeyAlias,
        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build()


    suspend fun saveLoginData(token: String, username: String, userId: Int) {
        if (tokenFile.exists()) {
            tokenFile.delete()
        }
        encryptedFile.openFileOutput().use { output ->
            output.write(token.toByteArray())
        }
        dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
            prefs[USER_ID_KEY] = userId
        }
    }

    val token: Flow<String?> = flow {
        try {
            encryptedFile.openFileInput().use { input ->
                val tokenBytes = input.readBytes()
                emit(String(tokenBytes))
            }
        } catch (e: Exception) {
            emit(null)
        }
    }

    val username: Flow<String?> = dataStore.data.map { prefs ->
        prefs[USERNAME_KEY]
    }

    val userId: Flow<Int?> = dataStore.data.map { prefs ->
        prefs[USER_ID_KEY]
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
        if (tokenFile.exists()) {
            tokenFile.delete()
        }
    }
}