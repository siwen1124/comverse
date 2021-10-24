package edu.utcs.comicwiki.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.ui.creation.CreationViewModel
import edu.utcs.comicwiki.ui.posts.PostsFragment

class CollectionFragment : Fragment() {

    companion object {
        fun newInstance(): CollectionFragment {
            return CollectionFragment()
        }
    }

    private val postsViewModel: CreationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_collection, container, false)

        initView(root)
        initObservers()

        return root
    }

    private fun initObservers() {
    }

    private fun initView(root: View) {
    }
}