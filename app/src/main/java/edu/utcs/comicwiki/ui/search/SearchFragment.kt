package edu.utcs.comicwiki.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.MainActivity
import edu.utcs.comicwiki.R
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var characterAdapter: GenericItemSearchAdapter
    private lateinit var teamAdapter: GenericItemSearchAdapter
    private lateinit var storyArcAdapter: GenericItemSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_search, container, false)

        initView(root)
        initObservers()
        initActionSearch(root)

        return root
    }

    private fun initActionSearch(root: View) {
        val search = root.findViewById<EditText>(R.id.action_search)
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s == null)
                    return
                if (s.isEmpty()) {
                    viewModel.clearAllSearchedResults()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                if (s == null)
                    return
                if (s.isEmpty()) {
                    viewModel.clearAllSearchedResults()
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // folding keyboard and clear views
                if (s.isEmpty()) {
                    title1.visibility = View.INVISIBLE
                    title2.visibility = View.INVISIBLE
                    title3.visibility = View.INVISIBLE
                    (context as MainActivity).hideKeyboard()
                    viewModel.clearAllSearchedResults()
                }
                else {
                    // searching on fly
                    title1.visibility = View.VISIBLE
                    title2.visibility = View.VISIBLE
                    title3.visibility = View.VISIBLE
                    viewModel.netFetch_Search(s.toString(), "character")
                    viewModel.netFetch_Search(s.toString(), "team")
                    viewModel.netFetch_Search(s.toString(), "story_arc")
                }
            }
        })
    }

    private fun initObservers() {

        viewModel.observeSearchResults("character").observe(viewLifecycleOwner, Observer {
            characterAdapter.notifyDataSetChanged()
        })

        viewModel.observeSearchResults("team").observe(viewLifecycleOwner, Observer {
            teamAdapter.notifyDataSetChanged()

        })

        viewModel.observeSearchResults("story_arc").observe(viewLifecycleOwner, Observer {
            storyArcAdapter.notifyDataSetChanged()
        })

    }

    private fun initView(root: View) {
        val rv_characters = root.findViewById<RecyclerView>(R.id.rv_characters)
        characterAdapter = GenericItemSearchAdapter(viewModel, "character")
        rv_characters.adapter = characterAdapter
        rv_characters.layoutManager = GridLayoutManager(context, 7)

        val rv_teams = root.findViewById<RecyclerView>(R.id.rv_locations)
        teamAdapter = GenericItemSearchAdapter(viewModel, "team")
        rv_teams.apply {
            adapter = teamAdapter
            layoutManager = GridLayoutManager(context, 7)
        }

        val rv_storyArcs = root.findViewById<RecyclerView>(R.id.rv_storyArcs)
        storyArcAdapter = GenericItemSearchAdapter(viewModel, "story_arc")
        rv_storyArcs.apply {
            adapter = storyArcAdapter
            layoutManager = GridLayoutManager(context, 7)
        }
    }
}