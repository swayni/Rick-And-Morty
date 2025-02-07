package sw.swayni.rickandmorty.ui.favorite_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import sw.swayni.basemvvm.mvvm.adapter.BaseRecyclerViewAdapter
import sw.swayni.basemvvm.mvvm.adapter.BaseViewHolder
import sw.swayni.rickandmorty.data.model.Character
import sw.swayni.rickandmorty.databinding.ItemCharacterBinding

class FavoriteCharacterListAdapter (listener: (character: Character) -> Unit) : BaseRecyclerViewAdapter<Character>() {

    init {
        this.itemClickListener = listener
    }
    override fun getCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Character> = CharacterViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))

    class CharacterViewHolder (private val binding: ItemCharacterBinding): BaseViewHolder<Character>(binding){
        override fun bindData(data: Character) {
            binding.image.load(data.image){
                crossfade(true)
                size(200, 200)
            }
            binding.characterName.text = data.name
        }
    }
}