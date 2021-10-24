package edu.utcs.comicwiki.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.ui.creation.CreationViewModel

class PostsFragment : Fragment() {
    companion object {
        fun newInstance(): PostsFragment {
            return PostsFragment()
        }
    }

    private val viewModel: CreationViewModel by activityViewModels()
    private lateinit var globalComicNodesAdapter: GlobalComicNodesAdapter
    private lateinit var userComicNodesAdapter: UserComicNodesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_posts, container, false)
        initView(root)
        initObservers()
        return root
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initObservers() {
        viewModel.observeGlobalComicNodes().observe(viewLifecycleOwner, Observer {
            globalComicNodesAdapter.notifyDataSetChanged()
        })
        viewModel.observeUserComicNodes().observe(viewLifecycleOwner, Observer {
            userComicNodesAdapter.notifyDataSetChanged()
        })
    }

    private fun initView(root: View) {
        globalComicNodesAdapter = GlobalComicNodesAdapter(viewModel)
        val rv_globalComicNodes = root.findViewById<RecyclerView>(R.id.rv_globalComicNodes)
        rv_globalComicNodes.adapter = globalComicNodesAdapter
        rv_globalComicNodes.layoutManager = GridLayoutManager(context, 7)

        val itemTouchHelper = initTouchHelper()
        userComicNodesAdapter = UserComicNodesAdapter(viewModel)
        val rv_userComicNodes = root.findViewById<RecyclerView>(R.id.rv_userComicNodes)
        rv_userComicNodes.adapter = userComicNodesAdapter
        rv_userComicNodes.layoutManager = GridLayoutManager(context, 7)
        itemTouchHelper.attachToRecyclerView(rv_userComicNodes)
    }

    private fun initTouchHelper(): ItemTouchHelper {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(
                UP or DOWN or START or END,
                ItemTouchHelper.START
            ) {
                override fun onSelectedChanged(
                    viewHolder: RecyclerView.ViewHolder?,
                    actionState: Int
                ) {
                    super.onSelectedChanged(viewHolder, actionState)
                    if (actionState == ACTION_STATE_DRAG) {
                        viewHolder?.itemView?.alpha = 0.5f
                    }
                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)
                    viewHolder.itemView.alpha = 1.0f
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition

                    viewModel.moveComicNode(from, to)
                    userComicNodesAdapter.notifyItemMoved(from, to)

                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                }
            }
        return ItemTouchHelper(simpleItemTouchCallback)
    }
}