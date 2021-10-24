package edu.utcs.comicwiki.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.model.Team
import edu.utcs.comicwiki.glide.Glide

class TeamsAdapter(private val viewModel: HomeViewModel) :
    RecyclerView.Adapter<TeamsAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var teamImage = itemView.findViewById<ImageView>(R.id.mainImage)
        private var deck = itemView.findViewById<TextView>(R.id.deck)

        init {
            itemView.setOnClickListener {
                val item = viewModel.getTeamsAt(adapterPosition)
                val name = item?.name ?:""
                val siteURL = item?.siteDetailURL
                val description = item?.description
                val imageURL = item?.image?.originalURL

                val action = HomeFragmentDirections.actionNavigationHomeToGenericItemFragment(imageURL,description,name)
                it.findNavController().navigate(action)
            }
        }

        fun bind(item: Team?) {
            deck.text = item?.deck
            Glide.fetch(item?.image!!.screenURL,item?.image.screenURL, teamImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_generic_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
       holder.bind(viewModel.getTeamsAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getTeamListCount()
    }

}