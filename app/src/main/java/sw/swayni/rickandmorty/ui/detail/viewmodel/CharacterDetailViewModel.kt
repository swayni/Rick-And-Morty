package sw.swayni.rickandmorty.ui.detail.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import sw.swayni.base.data.ResultData
import sw.swayni.basemvvm.di.quality.IoDispatcher
import sw.swayni.basemvvm.mvvm.enums.UiState
import sw.swayni.basemvvm.mvvm.viewmodel.BaseViewModel
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.data.repository.IRepository
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val repository: IRepository, @IoDispatcher private val dispatcher: CoroutineDispatcher) : BaseViewModel<CharacterDetailViewState, CharacterDetailAction>(
    CharacterDetailViewState()) {


    fun getCharacter(id: Int){
        sendAction(CharacterDetailAction.Loading)
        viewModelScope.launch (dispatcher) {
            repository.getCharacter(id).collect{
                when(it){
                    is ResultData.Error -> sendAction(CharacterDetailAction.Error(it.exception.message.toString()))
                    is ResultData.Success -> sendAction(CharacterDetailAction.CharacterSuccess(it.data))
                }
            }
        }
    }

    fun getEpisodeList(ids: String){
        sendAction(CharacterDetailAction.Loading)
        viewModelScope.launch (dispatcher) {
            repository.getEpisodeList(ids).collect {
                when (it) {
                    is ResultData.Error -> sendAction(CharacterDetailAction.Error(it.exception.message.toString()))
                    is ResultData.Success -> sendAction(CharacterDetailAction.EpisodeListSuccess(it.data))
                }
            }
        }
    }

    fun getFavoriteCharacter(id: Int){
        sendAction(CharacterDetailAction.Loading)
        viewModelScope.launch (dispatcher) {
            repository.getFavoriteCharacterWithId(id).collect{
                when(it){
                    is ResultData.Error -> sendAction(CharacterDetailAction.IsFavoriteCharacterSuccess(isFavorite = false))
                    is ResultData.Success -> sendAction(CharacterDetailAction.IsFavoriteCharacterSuccess(isFavorite = it.data))
                }
            }
        }
    }

    fun addFavoriteCharacter(character: Character){
        sendAction(CharacterDetailAction.Loading)
        viewModelScope.launch (dispatcher) {
            repository.addFavoriteCharacter(character).collect{
                when(it){
                    is ResultData.Error -> sendAction(CharacterDetailAction.IsFavoriteCharacterSuccess(false))
                    is ResultData.Success -> sendAction(CharacterDetailAction.IsFavoriteCharacterSuccess(it.data))
                }
            }
        }
    }

    fun removeFavoriteCharacter(character: Character){
        sendAction(CharacterDetailAction.Loading)
        viewModelScope.launch (dispatcher) {
            repository.removeFavoriteCharacter(character).collect{
                when(it){
                    is ResultData.Error -> sendAction(CharacterDetailAction.IsFavoriteCharacterSuccess(true))
                    is ResultData.Success -> sendAction(CharacterDetailAction.IsFavoriteCharacterSuccess(it.data))
                }
            }
        }
    }

    override fun cleanState(): CharacterDetailViewState = state.copy(uiState = UiState.IDLE, isFavorite = null, character = null, episodeList = null, errorMessage = null, errorCode = null)

    override fun onReduceState(viewAction: CharacterDetailAction): CharacterDetailViewState = when(viewAction){
        is CharacterDetailAction.Error -> state.copy(uiState = UiState.ERROR, isFavorite = null, character = null, episodeList = null, errorCode = null, errorMessage = viewAction.errorMessage)
        CharacterDetailAction.Loading -> state.copy(uiState = UiState.LOADING, isFavorite = null, character = null, episodeList = null, errorCode = null, errorMessage = null)
        is CharacterDetailAction.CharacterSuccess -> state.copy(uiState = UiState.SUCCESS, isFavorite = null, character = viewAction.character, episodeList = null, errorCode = null, errorMessage = null)
        is CharacterDetailAction.EpisodeListSuccess -> state.copy(uiState = UiState.SUCCESS, isFavorite = null, character = null, episodeList = viewAction.episodeList, errorCode = null, errorMessage = null)
        is CharacterDetailAction.IsFavoriteCharacterSuccess -> state.copy(uiState = UiState.SUCCESS, character = null, episodeList = null, errorCode = null, errorMessage = null, isFavorite = viewAction.isFavorite)
    }
}