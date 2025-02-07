package sw.swayni.rickandmorty.ui.list.viewmodel

import sw.swayni.basemvvm.mvvm.viewmodel.BaseAction
import sw.swayni.rickandmorty.data.model.AllCharactersModel

sealed class CharacterListAction : BaseAction {

    data class AllCharactersModelSuccess(val allCharactersModel: AllCharactersModel) : CharacterListAction()
    data object Loading : CharacterListAction()
    data class Error(val errorMessage: String) : CharacterListAction()
}