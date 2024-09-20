package kang.min.gu

import androidx.compose.runtime.Immutable
import kang.min.gu.base.Reducer

class MainScreenReducer :
    Reducer<MainScreenReducer.MainScreenState, MainScreenReducer.MainScreenEvent, MainScreenReducer.MainScreenEffect> {
    @Immutable
    data class MainScreenState(
        val storageText: String = ""
    ) : Reducer.ViewState

    @Immutable
    sealed interface MainScreenEvent : Reducer.ViewEvent {
        sealed interface LoadText : MainScreenEvent {
            data class InitText(val text: String) : MainScreenEvent.LoadText
            data class LoadText(val text: String) : MainScreenEvent.LoadText
        }
        data class SaveText(val text: String) : MainScreenEvent
    }

    @Immutable
    sealed interface MainScreenEffect : Reducer.ViewEffect {
        data class ShowToast(val message: String) : MainScreenEffect
    }

    override fun reduce(
        previousState: MainScreenState,
        event: MainScreenEvent
    ): Pair<MainScreenState, MainScreenEffect?> {
        return when(event) {
            is MainScreenEvent.LoadText.LoadText -> {
                previousState.copy(storageText = event.text) to MainScreenEffect.ShowToast("저장된 텍스트를 불러왔습니다.")
            }

            is MainScreenEvent.LoadText.InitText -> {
                previousState.copy(storageText = event.text) to null
            }

            is MainScreenEvent.SaveText -> {
                previousState.copy(storageText = event.text) to MainScreenEffect.ShowToast("텍스트를 저장했습니다.")
            }
        }
    }
}