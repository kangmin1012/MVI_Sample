package kang.min.gu.data.repository

import kang.min.gu.datastore.TextDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextRepository @Inject constructor(
    private val textDataSource: TextDataSource
) {
    suspend fun saveText(text: String) = withContext(Dispatchers.IO) {
        textDataSource.saveText(text)
    }

    fun loadTextFlow() = textDataSource.getTextFlow()
}