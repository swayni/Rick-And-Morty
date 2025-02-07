package sw.swayni.basemvvm.mvvm.viewmodel

import sw.swayni.basemvvm.mvvm.enums.UiState

interface BaseViewState {
    val errorCode : Int?
    val errorMessage: String?
    val uiState: UiState
}