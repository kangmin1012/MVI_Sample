package kang.min.gu

import dagger.hilt.android.lifecycle.HiltViewModel
import kang.min.gu.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() :
    BaseViewModel<MainScreenReducer.MainScreenState, MainScreenReducer.MainScreenEvent, MainScreenReducer.MainScreenEffect>(
        initialState = MainScreenReducer.MainScreenState(storageText = ""),
        reducer = MainScreenReducer()
    ) {

        init {
            sendEvent(MainScreenReducer.MainScreenEvent.LoadText.InitText(""))
        }

        fun loadText() {
            sendEvent(MainScreenReducer.MainScreenEvent.LoadText.LoadText(""))
        }

        fun saveText(text: String) {
            sendEvent(MainScreenReducer.MainScreenEvent.SaveText(text))
        }
}