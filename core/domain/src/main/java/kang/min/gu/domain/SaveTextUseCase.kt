package kang.min.gu.domain

import kang.min.gu.data.repository.TextRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveTextUseCase @Inject constructor(
    private val textRepository: TextRepository
) {
    suspend operator fun invoke(text: String) {
        textRepository.saveText(text)
    }
}