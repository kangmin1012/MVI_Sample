package kang.min.gu

import androidx.compose.runtime.Immutable
import kang.min.gu.base.Reducer

class MainScreenReducer :
    Reducer<MainScreenReducer.MainScreenState, MainScreenReducer.MainScreenEvent, MainScreenReducer.MainScreenEffect> {
    @Immutable
    data class MainScreenState(
        val storageText: String? = null,
        val isShowSavedText: Boolean = false
    ) : Reducer.ViewState

    @Immutable
    sealed interface MainScreenEvent : Reducer.ViewEvent {
        data class LoadText(val text: String?) : MainScreenEvent
        data class SaveText(val text: String?) : MainScreenEvent
    }

    @Immutable
    sealed interface MainScreenEffect : Reducer.ViewEffect {
        data class ShowToast(val message: String) : MainScreenEffect
    }

    override fun reduce(
        previousState: MainScreenState,
        event: MainScreenEvent
    ): Pair<MainScreenState, MainScreenEffect?> {
        return when (event) {
            is MainScreenEvent.LoadText -> {
                previousState.copy(storageText = event.text, isShowSavedText = true) to MainScreenEffect.ShowToast("저장된 텍스트를 불러왔습니다.")
            }

            is MainScreenEvent.SaveText -> {
                previousState.copy(storageText = event.text, isShowSavedText = false) to MainScreenEffect.ShowToast("텍스트를 저장했습니다.")
            }
        }
    }
}