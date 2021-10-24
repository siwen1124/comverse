package edu.utcs.comicwiki.ui.creation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.MainActivity
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.ui.search.GenericItemSearchAdapter
import edu.utcs.comicwiki.ui.search.GenericItemSearchAdapterII
import edu.utcs.comicwiki.ui.search.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class ComicNodeSearchActivity : AppCompatActivity() {
    companion object {
        const val nameKey = "nameKey"
        const val deckKey = "deckKey"
        const val smallImageURLKey = "smallImageURLKey"
        const val largeImageURLKey = "largeImageURLKey"
        const val apiDetailURLKey = "apiDetailURLKey"
    }

//    private val searchViewModel = SearchViewModel()
//    private lateinit var searchAdapter: ComicNodeSearchAdapter

    private val viewModel = SearchViewModel()
    private lateinit var characterAdapter: GenericItemSearchAdapterII
    private lateinit var teamAdapter: GenericItemSearchAdapterII
    private lateinit var storyArcAdapter: GenericItemSearchAdapterII

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Search"

        initView()
        initObservers()
        initActionSearch()
    }

    private fun initActionSearch() {
//        val search = findViewById<EditText>(R.id.action_search)
//        search.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                return
//            }
//
//            override fun beforeTextChanged(
//                s: CharSequence?,
//                start: Int,
//                count: Int,
//                after: Int
//            ) {
//                return
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                searchViewModel.netFetch_SearchCharacter(s.toString())
//            }
//        })
        val search = findViewById<EditText>(R.id.action_search)
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                return
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                return
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // folding keyboard and clear views
                if (s.isEmpty()) {
                    title1.visibility = View.INVISIBLE
                    title2.visibility = View.INVISIBLE
                    title3.visibility = View.INVISIBLE
                    viewModel.netFetch_Search("", "")
                } else {
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
//        searchViewModel.observeSearchCharacterResult().observe(this, Observer {
//            searchAdapter.notifyDataSetChanged()
//        })

        viewModel.observeSearchResults("character").observe(this, Observer {
            characterAdapter.notifyDataSetChanged()
        })

        viewModel.observeSearchResults("team").observe(this, Observer {
            teamAdapter.notifyDataSetChanged()

        })

        viewModel.observeSearchResults("story_arc").observe(this, Observer {
            storyArcAdapter.notifyDataSetChanged()
        })
    }

    private fun initView() {
//        val rv_search = findViewById<RecyclerView>(R.id.rv_globalComicNodes)
//        searchAdapter = ComicNodeSearchAdapter(searchViewModel, this)
//        rv_search.adapter = searchAdapter
//        rv_search.layoutManager = GridLayoutManager(this, 7)

        val rv_characters = findViewById<RecyclerView>(R.id.rv_characters)
        characterAdapter = GenericItemSearchAdapterII(viewModel, "character",this)
        rv_characters.adapter = characterAdapter
        rv_characters.layoutManager = GridLayoutManager(this, 7)

        val rv_teams = findViewById<RecyclerView>(R.id.rv_locations)
        teamAdapter = GenericItemSearchAdapterII(viewModel, "team",this)
        rv_teams.apply {
            adapter = teamAdapter
            layoutManager = GridLayoutManager(context, 7)
        }

        val rv_storyArcs = findViewById<RecyclerView>(R.id.rv_storyArcs)
        storyArcAdapter = GenericItemSearchAdapterII(viewModel, "story_arc",this)
        rv_storyArcs.apply {
            adapter = storyArcAdapter
            layoutManager = GridLayoutManager(context, 7)
        }
    }
}