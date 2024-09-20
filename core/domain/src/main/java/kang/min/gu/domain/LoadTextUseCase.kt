package kang.min.gu.domain

import kang.min.gu.data.repository.TextRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadTextUseCase @Inject constructor(
    private val textRepository: TextRepository
) {
    suspend operator fun invoke(): String? {
        return textRepository.loadTextFlow().firstOrNull()
    }
}