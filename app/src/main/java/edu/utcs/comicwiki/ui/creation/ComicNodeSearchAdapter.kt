package edu.utcs.comicwiki.ui.creation

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.model.Character
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.apiDetailURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.deckKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.largeImageURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.nameKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.smallImageURLKey
import edu.utcs.comicwiki.ui.search.SearchViewModel

class ComicNodeSearchAdapter(
    private val comicNodeSearchViewModel: SearchViewModel,
    private val activity: Activity
) :
    RecyclerView.Adapter<ComicNodeSearchAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var searchImage = itemView.findViewById<ImageView>(R.id.iconImage)
        private var deck = itemView.findViewById<TextView>(R.id.deck)

        init {
            itemView.setOnClickListener {
                val returnIntent = Intent().apply {
                    val result =
                        comicNodeSearchViewModel.getSearchCharacterResultsAt(adapterPosition)
                    this.putExtra(nameKey, result?.name)
                    this.putExtra(deckKey, result?.deck)
                    this.putExtra(smallImageURLKey, result?.image?.iconURL)
                    this.putExtra(largeImageURLKey, result?.image?.mediumURL)
                    this.putExtra(apiDetailURLKey, result?.apiDetailURL)
                }
                activity.setResult(0, returnIntent)
                activity.finish()
            }
        }

        fun bind(item: Character?) {
            itemView.tooltipText = item?.deck
            Glide.fetch(item?.image!!.iconURL, item.image.smallURL, searchImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_search, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(comicNodeSearchViewModel.getSearchCharacterResultsAt(position))
    }

    override fun getItemCount(): Int {
        return comicNodeSearchViewModel.getSearchCharacterResultsCount()
    }
}