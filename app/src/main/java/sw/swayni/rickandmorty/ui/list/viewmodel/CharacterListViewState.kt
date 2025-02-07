package sw.swayni.rickandmorty.ui.list.viewmodel

import sw.swayni.basemvvm.mvvm.enums.UiState
import sw.swayni.basemvvm.mvvm.viewmodel.BaseViewState
import sw.swayni.rickandmorty.data.model.AllCharactersModel

data class CharacterListViewState(
    val allCharactersModel : AllCharactersModel? = null,
    override val errorCode: Int? = null,
    override val errorMessage: String? = null,
    override val uiState: UiState = UiState.IDLE
) : BaseViewState
