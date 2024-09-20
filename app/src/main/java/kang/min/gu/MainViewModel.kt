package kang.min.gu

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kang.min.gu.base.BaseViewModel
import kang.min.gu.domain.LoadTextUseCase
import kang.min.gu.domain.SaveTextUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadTextUseCase: LoadTextUseCase,
    private val saveTextUseCase: SaveTextUseCase
) : BaseViewModel<MainScreenReducer.MainScreenState, MainScreenReducer.MainScreenEvent, MainScreenReducer.MainScreenEffect>(
    initialState = MainScreenReducer.MainScreenState(storageText = ""),
    reducer = MainScreenReducer()
) {

    init {
        sendEvent(MainScreenReducer.MainScreenEvent.LoadText(""))
    }

    fun loadText() {
        viewModelScope.launch {
            sendEventWithEffect(MainScreenReducer.MainScreenEvent.LoadText(loadTextUseCase()))
        }
    }

    fun saveText(text: String) {
        viewModelScope.launch {
            saveTextUseCase(text)
            sendEventWithEffect(MainScreenReducer.MainScreenEvent.SaveText(text))
        }
    }
}