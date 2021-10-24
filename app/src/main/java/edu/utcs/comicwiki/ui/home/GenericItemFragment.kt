package edu.utcs.comicwiki.ui.home

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.ui.creation.CreationViewModel
import edu.utcs.comicwiki.ui.posts.PostsFragment

class GenericItemFragment : Fragment() {

    companion object {
        fun newInstance(): GenericItemFragment {
            return GenericItemFragment()
        }
    }

    private val viewModel: CreationViewModel by activityViewModels()
    private val args: GenericItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_generic_item, container, false)

        initView(root)
        initObservers()

        return root
    }

    private fun initObservers() {
    }

    private fun initView(root: View) {
        val mainImage = root.findViewById<ImageView>(R.id.mainImage)
        val description = root.findViewById<TextView>(R.id.description)

        if (args.imageURL != null)
            Glide.fetch(args.imageURL!!, args.imageURL!!, mainImage)
        if (args.description != null)
            description.text = Html.fromHtml(args.description, 0)
        else
            description.text = "No description"
    }
}