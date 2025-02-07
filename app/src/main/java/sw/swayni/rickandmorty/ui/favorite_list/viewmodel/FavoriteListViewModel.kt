package sw.swayni.rickandmorty.ui.favorite_list.viewmodel

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
class FavoriteListViewModel @Inject constructor(private val repository: IRepository, @IoDispatcher private val dispatcher: CoroutineDispatcher) : BaseViewModel<FavoriteListViewState, FavoriteListAction>(FavoriteListViewState()) {


    fun getFavoriteList(){
        sendAction(FavoriteListAction.Loading)
        viewModelScope.launch (dispatcher) {
            repository.getFavoriteCharacters().collect{
                when(it){
                    is ResultData.Error -> sendAction(FavoriteListAction.Error(it.exception.message.toString()))
                    is ResultData.Success -> sendAction(FavoriteListAction.FavoriteListSuccess(it.data))
                }
            }
        }
    }

    override fun cleanState(): FavoriteListViewState = state.copy(uiState = UiState.IDLE, errorCode = null, errorMessage = null)

    override fun onReduceState(viewAction: FavoriteListAction): FavoriteListViewState = when (viewAction) {
        is FavoriteListAction.Error -> state.copy(uiState = UiState.ERROR, favoriteList=null, errorCode = null, errorMessage = viewAction.message)
        is FavoriteListAction.FavoriteListSuccess -> state.copy(uiState = UiState.SUCCESS, errorCode = null, errorMessage = null, favoriteList = viewAction.favoriteList)
        FavoriteListAction.Loading -> state.copy(uiState = UiState.LOADING, favoriteList=null, errorCode = null, errorMessage = null)
    }
}