package sw.swayni.rickandmorty.ui.favorite_list.viewmodel

import sw.swayni.basemvvm.mvvm.enums.UiState
import sw.swayni.basemvvm.mvvm.viewmodel.BaseViewState
import sw.swayni.rickandmorty.data.model.Character

data class FavoriteListViewState(
    val favoriteList: List<Character>? = null,
    override val errorCode: Int? = null,
    override val errorMessage: String? = null,
    override val uiState: UiState = UiState.IDLE
) : BaseViewState
