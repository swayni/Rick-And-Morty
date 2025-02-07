package sw.swayni.rickandmorty.ui.detail.presentation

import android.annotation.SuppressLint
import android.app.Dialog
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import sw.swayni.basemvvm.mvvm.core.observe
import sw.swayni.basemvvm.mvvm.enums.UiState
import sw.swayni.basemvvm.mvvm.view.BindingFragment
import sw.swayni.rickandmorty.R
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.databinding.FragmentCharacterDetailBinding
import sw.swayni.rickandmorty.ui.detail.adapter.EpisodesListAdapter
import sw.swayni.rickandmorty.ui.detail.viewmodel.CharacterDetailViewModel

@AndroidEntryPoint
class CharacterDetailFragment : BindingFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>(layoutId = R.layout.fragment_character_detail, CharacterDetailViewModel::class.java) {

    private val navArgs by navArgs<CharacterDetailFragmentArgs>()
    private val characterId by lazy { navArgs.id }

    private val adapter by lazy { EpisodesListAdapter() }
    private var isExpandRecyclerview = false

    private var isFavorite = false
    private var character : Character? = null

    override fun initUI() {
        binding.listViewEpisodes.adapter = adapter

        viewModel.getCharacter(characterId)
        viewModel.getFavoriteCharacter(characterId)

        binding.expandAndCollapseButton.setOnClickListener {
            expandAndCollapseRecyclerView()
        }

        binding.closeButton.setOnClickListener {
            viewModel.navigateBack()
        }

        binding.buttonFavorite.setOnClickListener {
            character?.let {
                if (isFavorite){
                    viewModel.removeFavoriteCharacter(it)
                }else{
                    viewModel.addFavoriteCharacter(it)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun createdObserve() {
        observe(viewModel.uiStateFlow){
            showLoading(it.uiState)
            when(it.uiState){
                UiState.ERROR ->{
                    AlertDialog.Builder(requireContext()).setMessage(it.errorMessage).setPositiveButton("Ok!"
                    ) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
                    Log.e("error", it.errorMessage.toString())
                }
                UiState.SUCCESS -> {
                    it.character?.let { character ->
                        this.character = character
                        binding.image.load(character.image){
                            crossfade(true)
                            size(200, 200)
                        }
                        binding.nameText.text = character.name
                        binding.statusSpeciesText.text = "${character.status}, ${character.species}"
                        binding.genderText.text = character.gender
                        var idsString = ""
                        character.episode?.forEach { url->
                            idsString = "${idsString},${url.substring(40)}"
                        }
                        viewModel.getEpisodeList(idsString.substring(1))
                    }
                    it.episodeList?.let { episodeList->
                        adapter.addList(episodeList)
                    }
                    it.isFavorite?.let { isFavorite->
                        this.isFavorite = isFavorite
                        if (isFavorite){
                            binding.buttonFavorite.setImageResource(R.drawable.ic_favorite_red)
                        }else {
                            binding.buttonFavorite.setImageResource(R.drawable.ic_favorite_white)
                        }
                    }
                }
                else ->{}
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

    private fun expandAndCollapseRecyclerView() {
        if(isExpandRecyclerview){
            binding.listViewEpisodes.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.collapse))
            binding.expandAndCollapseButton.setImageResource(R.drawable.icon_arrow_drop_down)
            isExpandRecyclerview=false
        }else{
            binding.listViewEpisodes.visibility = VISIBLE
            binding.listViewEpisodes.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.expand))
            binding.expandAndCollapseButton.setImageResource(R.drawable.icon_arrow_drop_up)
            isExpandRecyclerview = true
        }

    }
}