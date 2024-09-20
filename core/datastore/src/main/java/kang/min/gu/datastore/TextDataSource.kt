package kang.min.gu.datastore

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextDataSource @Inject constructor(
    private val textDataStore: TextDataStore
) {
    suspend fun saveText(text: String) {
        textDataStore.setText(text)
    }

    fun getTextFlow() = textDataStore.keyTextFlow
}