package sw.swayni.rickandmorty.ui.favorite_list.viewmodel

import sw.swayni.basemvvm.mvvm.viewmodel.BaseAction
import sw.swayni.rickandmorty.data.model.Character

sealed class FavoriteListAction : BaseAction {

    data class FavoriteListSuccess(val favoriteList: List<Character>) : FavoriteListAction()
    data class Error(val message: String) : FavoriteListAction()
    data object Loading : FavoriteListAction()
}