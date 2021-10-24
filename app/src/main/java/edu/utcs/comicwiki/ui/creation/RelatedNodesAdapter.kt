package edu.utcs.comicwiki.ui.creation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.ui.creation.CreationViewModel

class RelatedNodesAdapter(
    private val viewModel: CreationViewModel
) : RecyclerView.Adapter<RelatedNodesAdapter.VH>() {
    @SuppressLint("ClickableViewAccessibility")
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nodeImage = itemView.findViewById<ImageView>(R.id.nodeImage)

        init {
            itemView.setOnLongClickListener {
                viewModel.deleteRelatedNodesAt(adapterPosition)
                true
            }
        }

        fun bind(item: ComicNode?) {
            if (item != null) {
                Glide.fetch(item.smallImageURL!!, item.smallImageURL!!, nodeImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_post, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(viewModel.getRelatedNodesAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getRelatedNodesCount()
    }
}