package sw.swayni.rickandmorty.ui.detail.viewmodel

import sw.swayni.basemvvm.mvvm.enums.UiState
import sw.swayni.basemvvm.mvvm.viewmodel.BaseViewState
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.data.model.Episode

data class CharacterDetailViewState(
    val character: Character? = null,
    val episodeList: List<Episode>? = null,
    val isFavorite : Boolean? = null,
    override val errorCode: Int? = null,
    override val errorMessage: String? = null,
    override val uiState: UiState = UiState.IDLE
) : BaseViewState
