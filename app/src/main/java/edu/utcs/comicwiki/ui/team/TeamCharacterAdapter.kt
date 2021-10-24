package edu.utcs.comicwiki.ui.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.model.Character
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.ui.MainViewModel

class TeamCharacterAdapter(private val viewModel: MainViewModel) :
    RecyclerView.Adapter<TeamCharacterAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var characterImage = itemView.findViewById<ImageView>(R.id.mainImage)
        init {
            itemView.setOnClickListener {

            }

        }

        fun bind(item: Character?) {
            if (item != null) {
                itemView.tooltipText = item.name
                Glide.fetch(item.image.smallURL, item.image.thumbURL, characterImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_team_character, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(viewModel.getTeamMemberAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getTeamMembersCount()
    }

}