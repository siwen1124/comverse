package edu.utcs.comicwiki.ui.creation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.model.RelatedNode
import kotlinx.android.synthetic.main.row_related_node.view.*

class RecyclerViewAdapter(private val viewModel: CreationViewModel) :
    RecyclerView.Adapter<RecyclerViewAdapter.VH>() {

    @SuppressLint("ClickableViewAccessibility")
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.name
        private val deck: TextView = itemView.deck
        private val image: ImageView = itemView.image

        fun bind(item: ComicNode?) {
            name.text = item?.name
            deck.text = item?.deck
            name.isVisible = false
            deck.isVisible = false
            if (item != null) {
                Glide.fetch(item.largeImageURL, item.smallImageURL, image)
                image.clipToOutline = true
            }
        }

        init {
            itemView.setOnLongClickListener {
                viewModel.deleteRelatedNodesAt(adapterPosition)
                Toast.makeText(it.context, "Related node deleted.", Toast.LENGTH_SHORT).show()
                true
            }
            itemView.setOnClickListener {
                name.isVisible = !name.isVisible
                deck.isVisible = !deck.isVisible
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_related_node, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.VH, position: Int) {
        holder.bind(viewModel.getRelatedNodesAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getRelatedNodesCount()
    }
}