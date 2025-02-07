package sw.swayni.rickandmorty.ui.main.viewmodel

import sw.swayni.basemvvm.mvvm.enums.UiState
import sw.swayni.basemvvm.mvvm.viewmodel.BaseViewState

data class MainViewState(
    override val errorCode: Int? = null,
    override val errorMessage: String? = null,
    override val uiState: UiState = UiState.IDLE
): BaseViewState
