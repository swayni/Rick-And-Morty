package sw.swayni.rickandmorty.ui.main.viewmodel

import sw.swayni.basemvvm.mvvm.viewmodel.BaseViewModel


class MainViewModel : BaseViewModel<MainViewState, MainAction>(MainViewState()) {


    override fun cleanState(): MainViewState = state.copy()

    override fun onReduceState(viewAction: MainAction): MainViewState = state.copy()
}