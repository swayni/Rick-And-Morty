package sw.swayni.rickandmorty.ui.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import sw.swayni.basemvvm.mvvm.adapter.BaseRecyclerViewAdapter
import sw.swayni.basemvvm.mvvm.adapter.BaseViewHolder
import sw.swayni.rickandmorty.data.model.Episode
import sw.swayni.rickandmorty.databinding.ItemEpisodeBinding

class EpisodesListAdapter : BaseRecyclerViewAdapter<Episode>() {

    override fun getCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<Episode> = EpisodeViewHolder(ItemEpisodeBinding.inflate(
        LayoutInflater.from(viewGroup.context), viewGroup, false))

    class EpisodeViewHolder(private val binding: ItemEpisodeBinding) : BaseViewHolder<Episode>(binding){
        @SuppressLint("SetTextI18n")
        override fun bindData(data: Episode) {
            binding.episodeText.text = "${data.name} (${episodeStringToFormat(data.episode)})"
        }

        private fun episodeStringToFormat(episodeString: String):String{
            val resultStringBuilder = StringBuilder()

            var term = episodeString.substring(0,3)
            resultStringBuilder.append(
                if (term[1] == '0'){ "${term.replace("0", "")} - " } else{ "$term -" }
            )

            term = episodeString.substring(3)
            resultStringBuilder.append(
                if (term[1] == '0'){term.replace("0", "")} else{term}
            )

            return resultStringBuilder.toString()
        }
    }
}