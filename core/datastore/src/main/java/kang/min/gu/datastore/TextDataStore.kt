package kang.min.gu.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kang.min.gu.datastore.TextDataStore.Companion.TEXT_STORE_PREFERENCES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.textDataStore by preferencesDataStore(name = TEXT_STORE_PREFERENCES)

@Singleton
class TextDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val keyText = stringPreferencesKey("text_string")

    val keyTextFlow: Flow<String?> = context.textDataStore.data.map { preference ->
        preference[keyText]
    }

    suspend fun setText(text: String) {
        context.textDataStore.edit { preference ->
            preference[keyText] = text
        }
    }

    companion object {
        const val TEXT_STORE_PREFERENCES = "text_store"
    }
}