package sw.swayni.rickandmorty.ui.list.presentation

import android.app.Dialog
import android.util.Log
import androidx.appcompat.app.AlertDialog
import dagger.hilt.android.AndroidEntryPoint
import sw.swayni.basemvvm.mvvm.core.observe
import sw.swayni.basemvvm.mvvm.enums.UiState
import sw.swayni.basemvvm.mvvm.view.BindingFragment
import sw.swayni.rickandmorty.R
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.databinding.FragmentCharacterListBinding
import sw.swayni.rickandmorty.ui.list.adapter.CharacterListAdapter
import sw.swayni.rickandmorty.ui.list.viewmodel.CharacterListViewModel

@AndroidEntryPoint
class CharacterListFragment : BindingFragment<FragmentCharacterListBinding, CharacterListViewModel>(layoutId = R.layout.fragment_character_list, CharacterListViewModel::class.java) {

    private val adapter by lazy { CharacterListAdapter(::itemClickListener) }

    override fun initUI() {
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
                    it.allCharactersModel?.let { charactersModel ->
                        adapter.updateList(charactersModel.result)
                    }
                }
                else -> {}
            }
        }
    }

    override fun showLoading(status: UiState?){
        if (loadingDialog == null && context != null){
            loadingDialog = Dialog(requireContext())
        }
        if (context != null) {
            loadingDialog?.let { loadingDialog->
                loadingDialog.setContentView(R.layout.component_progress_bar)
                loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                loadingDialog.setCanceledOnTouchOutside(false)
                if (status == UiState.LOADING) {
                    if (!loadingDialog.isShowing) {
                        loadingDialog.show()
                    }
                } else {
                    loadingDialog.dismiss()
                }
            }
        }
    }

    private fun itemClickListener(character : Character){
        viewModel.navigate(CharacterListFragmentDirections.actionFragmentCharacterListToFragmentCharacterDetail(character.id))
    }
}