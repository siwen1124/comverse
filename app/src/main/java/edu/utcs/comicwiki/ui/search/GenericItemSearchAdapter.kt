package edu.utcs.comicwiki.ui.search

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

class GenericItemSearchAdapter(
    private val viewModel: SearchViewModel,
    private val resources: String
) :
    RecyclerView.Adapter<GenericItemSearchAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var iconImage = itemView.findViewById<ImageView>(R.id.iconImage)
        private var deck = itemView.findViewById<TextView>(R.id.deck)

        init {
            itemView.setOnClickListener {
                // navigate to fragment
                val item = viewModel.getSearchResultsAt(adapterPosition, resources)
                val action = SearchFragmentDirections.actionNavigationSearchToGenericItemFragment(
                    item?.image?.mediumURL,
                    item?.description,
                    item?.name ?:""
                )
                if (action != null)
                    it.findNavController().navigate(action)

                // corresponding UI change
                (it.context as MainActivity).hideKeyboard()
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