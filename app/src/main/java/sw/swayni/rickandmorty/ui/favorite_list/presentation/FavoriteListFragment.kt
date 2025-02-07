package sw.swayni.rickandmorty.ui.favorite_list.presentation

import android.util.Log
import androidx.appcompat.app.AlertDialog
import dagger.hilt.android.AndroidEntryPoint
import sw.swayni.basemvvm.mvvm.core.observe
import sw.swayni.basemvvm.mvvm.enums.UiState
import sw.swayni.basemvvm.mvvm.view.BindingFragment
import sw.swayni.rickandmorty.R
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.databinding.FragmentFavoriteListBinding
import sw.swayni.rickandmorty.ui.favorite_list.adapter.FavoriteCharacterListAdapter
import sw.swayni.rickandmorty.ui.favorite_list.viewmodel.FavoriteListViewModel

@AndroidEntryPoint
class FavoriteListFragment : BindingFragment<FragmentFavoriteListBinding, FavoriteListViewModel>(layoutId = R.layout.fragment_favorite_list, FavoriteListViewModel::class.java)  {

    private val adapter by lazy { FavoriteCharacterListAdapter(::itemClickListener) }

    override fun initUI() {
        viewModel.getFavoriteList()
        binding.charactersRecyclerView.adapter = adapter
    }

    override fun createdObserve() {
        observe(viewModel.uiStateFlow){
            showLoading(it.uiState)
            when(it.uiState){
                UiState.ERROR -> {
                    AlertDialog.Builder(requireContext()).setMessage(it.errorMessage).setPositiveButton("Ok!"
                    ) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
                    Log.e("error", it.errorMessage.toString())
                }
                UiState.SUCCESS -> {
                    it.favoriteList?.let { favoriteList->
                        adapter.updateList(favoriteList)
                    }
                }
                else->{}
            }
        }
    }

    private fun itemClickListener(character : Character){
        viewModel.navigate(FavoriteListFragmentDirections.actionFragmentFavoriteListToFragmentCharacterDetail(character.id))
    }
}