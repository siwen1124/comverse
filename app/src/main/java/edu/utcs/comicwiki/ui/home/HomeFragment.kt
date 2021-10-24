package edu.utcs.comicwiki.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var objectsAdapter: ObjectsAdapter
    private lateinit var locationsAdapter: LocationsAdapter
    private lateinit var conceptsAdapter: ConceptsAdapter
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        initView(root)
        initObservers(root)
        initActions(root)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initObservers(root: View) {
        homeViewModel.observeRandomItem().observe(viewLifecycleOwner, Observer {
            val tv = root.findViewById<ImageView>(R.id.doYouKnowImage)
            val deck = root.findViewById<TextView>(R.id.doYouKnowDeck)
            Glide.fetch(it.image.mediumURL, it.image.mediumURL, tv)
            deck.text = it.deck
        })
        homeViewModel.observeTeams().observe(viewLifecycleOwner, Observer {
            teamsAdapter.notifyDataSetChanged()
        })
        homeViewModel.observeCharacters().observe(viewLifecycleOwner, Observer {
            charactersAdapter.notifyDataSetChanged()
        })
        homeViewModel.observeObjects().observe(viewLifecycleOwner, Observer {
            objectsAdapter.notifyDataSetChanged()
        })
        homeViewModel.observeLocations().observe(viewLifecycleOwner, Observer {
            locationsAdapter.notifyDataSetChanged()
        })
        homeViewModel.observeConcepts().observe(viewLifecycleOwner, Observer {
            conceptsAdapter.notifyDataSetChanged()
        })
    }

    private fun initView(root: View) {

        val characterList_rv = root.findViewById<RecyclerView>(R.id.rv_characters)
        charactersAdapter = CharactersAdapter(homeViewModel)
        characterList_rv.adapter = charactersAdapter
        characterList_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val concept_rv = root.findViewById<RecyclerView>(R.id.rv_concepts)
        conceptsAdapter = ConceptsAdapter(homeViewModel)
        concept_rv.adapter = conceptsAdapter
        concept_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val teamList_rv = root.findViewById<RecyclerView>(R.id.rv_teams)
        teamsAdapter = TeamsAdapter(homeViewModel)
        teamList_rv.adapter = teamsAdapter
        teamList_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val locations_rv = root.findViewById<RecyclerView>(R.id.rv_locations)
        locationsAdapter = LocationsAdapter(homeViewModel)
        locations_rv.adapter = locationsAdapter
        locations_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val objects_rv = root.findViewById<RecyclerView>(R.id.rv_objects)
        objectsAdapter = ObjectsAdapter(homeViewModel)
        objects_rv.adapter = objectsAdapter
        objects_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initActions(root: View) {
        val tv = root.findViewById<ImageView>(R.id.doYouKnowImage)
        tv.setOnClickListener {
            val item = homeViewModel.getRandomItem()
            val name = item?.name ?:""
            val siteURL = item?.siteDetailURL
            val description = item?.description
            val imageURL = item?.image?.originalURL

            val action = HomeFragmentDirections.actionNavigationHomeToGenericItemFragment(imageURL,description,name)
            it.findNavController().navigate(action)
        }
    }
}