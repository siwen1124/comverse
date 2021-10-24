package edu.utcs.comicwiki.ui.search

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.MainActivity
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.GenericItem
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity

class GenericItemSearchAdapterII(
    private val viewModel: SearchViewModel,
    private val resources: String,
    private val activity: Activity
) :
    RecyclerView.Adapter<GenericItemSearchAdapterII.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var iconImage = itemView.findViewById<ImageView>(R.id.iconImage)
        private var deck = itemView.findViewById<TextView>(R.id.deck)

        init {
            itemView.setOnClickListener {
                val returnIntent = Intent().apply {
                    val result =
                        viewModel.getSearchResultsAt(adapterPosition,resources)
                    this.putExtra(ComicNodeSearchActivity.nameKey, result?.name)
                    this.putExtra(ComicNodeSearchActivity.deckKey, result?.deck)
                    this.putExtra(ComicNodeSearchActivity.smallImageURLKey, result?.image?.iconURL)
                    this.putExtra(ComicNodeSearchActivity.largeImageURLKey, result?.image?.mediumURL)
                    this.putExtra(ComicNodeSearchActivity.apiDetailURLKey, result?.apiDetailURL)
                }
                activity.setResult(0, returnIntent)
                activity.finish()
            }
        }

        fun bind(item: GenericItem?) {
            itemView.tooltipText = item?.deck
            Glide.fetch(item?.image!!.iconURL, item.image.smallURL, iconImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_search, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(viewModel.getSearchResultsAt(position, resources))
    }

    override fun getItemCount(): Int {
        return viewModel.getSearchResultsCount(resources)
    }
}