package sw.swayni.rickandmorty.ui.list.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import sw.swayni.base.data.ResultData
import sw.swayni.basemvvm.di.quality.IoDispatcher
import sw.swayni.basemvvm.mvvm.enums.UiState
import sw.swayni.basemvvm.mvvm.viewmodel.BaseViewModel
import sw.swayni.rickandmorty.data.repository.IRepository
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val repository: IRepository, @IoDispatcher private val dispatcher: CoroutineDispatcher) : BaseViewModel<CharacterListViewState, CharacterListAction>(
    CharacterListViewState()
) {

    init {
        getAllCharacters()
    }

    private fun getAllCharacters(){
        sendAction(CharacterListAction.Loading)
        viewModelScope.launch(dispatcher){
            repository.getAllCharacters().collect{
                when(it){
                    is ResultData.Error -> sendAction(CharacterListAction.Error(it.exception.message.toString()))
                    is ResultData.Success -> sendAction(CharacterListAction.AllCharactersModelSuccess(it.data))
                }
            }
        }
    }

    override fun cleanState(): CharacterListViewState = state.copy(uiState = UiState.IDLE, errorCode = null, errorMessage = null)

    override fun onReduceState(viewAction: CharacterListAction): CharacterListViewState = when(viewAction){
        is CharacterListAction.AllCharactersModelSuccess -> state.copy(uiState = UiState.SUCCESS, allCharactersModel = viewAction.allCharactersModel, errorCode = null, errorMessage = null)
        is CharacterListAction.Error -> state.copy(uiState = UiState.ERROR, allCharactersModel = null, errorCode = null, errorMessage = viewAction.errorMessage)
        CharacterListAction.Loading -> state.copy(uiState = UiState.LOADING, allCharactersModel = null, errorCode = null, errorMessage = null)
    }
}