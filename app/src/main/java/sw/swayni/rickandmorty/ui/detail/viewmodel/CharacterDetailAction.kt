package sw.swayni.rickandmorty.ui.detail.viewmodel

import sw.swayni.basemvvm.mvvm.viewmodel.BaseAction
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.data.model.Episode

sealed class CharacterDetailAction : BaseAction {

    data object Loading : CharacterDetailAction()
    data class Error(val errorMessage: String) : CharacterDetailAction()
    data class CharacterSuccess(val character: Character) : CharacterDetailAction()
    data class EpisodeListSuccess(val episodeList: List<Episode>) : CharacterDetailAction()
    data class IsFavoriteCharacterSuccess(val isFavorite: Boolean) : CharacterDetailAction()
}