package edu.utcs.comicwiki.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.ui.MainViewModel

class TeamFragment : Fragment() {
    companion object {
        fun newInstance(): TeamFragment {
            return TeamFragment()
        }
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var teamMemberAdapter: TeamCharacterAdapter
    private lateinit var friendsAdapter: TeamCharacterAdapter
    private lateinit var enemiesAdapter: TeamCharacterAdapter

    //TODO: data observer
    private fun initObservers() {
        viewModel.observeTeam().observe(viewLifecycleOwner, Observer {
            viewModel.netFetch_teamMembers()
            viewModel.netFetch_teamFriends()
            viewModel.netFetch_teamEnemies()
        })
        viewModel.observeTeamMembers().observe(viewLifecycleOwner, Observer {
            teamMemberAdapter.notifyDataSetChanged()
        })

    }

    // TODO: set up adapter
    private fun initView(root: View) {
        // TODO: teamMembers
        val teamMembers_rv = root.findViewById<RecyclerView>(R.id.teamMembers_rv)
        teamMemberAdapter = TeamCharacterAdapter(viewModel)
        teamMembers_rv.adapter = teamMemberAdapter
        teamMembers_rv.layoutManager =
            GridLayoutManager(context, 5, GridLayoutManager.VERTICAL, false)


        // TODO: action bar
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_team, container, false)
//        root.findViewById<WebView>(R.id.webView)
//            .loadUrl("https://comicvine.gamespot.com/ani-men/4060-2508/")
//        val a =
//            "<h2>Brief History</h2><p>The original lineup of Ani-Men (<a href=\"/ape-man/4005-14500/\" data-ref-id=\"4005-14500\"> <u> </u>Ape-Man</a>, <a href=\"/bird-man/4005-14503/\" data-ref-id=\"4005-14503\"> Bird-Man</a>, <a href=\"/cat-man/4005-14501/\" data-ref-id=\"4005-14501\"> Cat-Man</a>, <a href=\"/frog-man/4005-14502/\" data-ref-id=\"4005-14502\"> Frog-Man</a>) were recruited by a man named Abner Jonas under the guise of the <a href=\"/organizer/4005-38822/\" data-ref-id=\"4005-38822\">Organizer.</a> Abner Jonas was a candidate for mayor of New York City, who sends the Ani-Men on missions to undermine the current administration. <a href=\"/daredevil/4005-24694/\" data-ref-id=\"4005-24694\"> Daredevil</a> defeated both the Ani-Men and their leader and sent them all to prison.</p><p>Later, Ape-Man, Bird-Man, and Cat-Man formed a team refered to as the <em>Unholy Three</em> and worked with the Exterminator (who eventually becomes the <a href=\"/death-stalker/4005-10364/\" data-ref-id=\"4005-10364\"> Death-Stalker</a> and <a href=\"/count-nefaria/4005-3229/\" data-ref-id=\"4005-3229\"> Count Nefaria)</a>. The Ani-Men served as henchmen to the count and have come at odds with various heroes. They would go on to under-go a treatment by Count's scientist to become more animal like. </p><p> </p><p>A new band of Ani-Men has risen. These are created by the High Evolutinary and are his servants.</p><h2>Creation</h2><p>The Ani-Men were created by <a href=\"/wally-wood/4040-6099/\" data-ref-id=\"4040-6099\"> Wally Wood</a> in 1965 and first appeared in <a href=\"/daredevil/4050-2190/\" data-ref-id=\"4050-2190\"> Daredevil</a> #10.</p>"
//        root.findViewById<TextView>(R.id.html).text = Html.fromHtml(a, 0)

        initView(root)
        initObservers()

        return root
    }
}