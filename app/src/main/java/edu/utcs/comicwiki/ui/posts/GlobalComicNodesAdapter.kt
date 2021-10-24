package edu.utcs.comicwiki.ui.posts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.ui.creation.CreationViewModel

class GlobalComicNodesAdapter(
    private val viewModel: CreationViewModel
) : RecyclerView.Adapter<GlobalComicNodesAdapter.VH>() {
    @SuppressLint("ClickableViewAccessibility")
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nodeImage = itemView.findViewById<ImageView>(R.id.nodeImage)

        init {
            itemView.setOnClickListener {
                val item = viewModel.getGlobalComicNodesAt(adapterPosition)
                item?.let {
                    viewModel.setCenterNode(it)
                    viewModel.setRelatedNodes(it.relatedNodes)
                    viewModel.setUserDescription(it.userDescription)
                }

                val action = PostsFragmentDirections.actionNavigationPostsToNavigationCreation()
                it.findNavController().navigate(action)
            }
        }

        fun bind(item: ComicNode?) {
            if (item != null) {
                Glide.fetch(item.smallImageURL!!, item.smallImageURL!!, nodeImage)
                nodeImage.tooltipText = viewModel.getGlobalComicNodesAt(adapterPosition)?.name
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
        holder.bind(viewModel.getGlobalComicNodesAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getGlobalComicNodesCount()
    }
}