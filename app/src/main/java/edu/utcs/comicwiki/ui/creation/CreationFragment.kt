package edu.utcs.comicwiki.ui.creation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.kapil.circularlayoutmanager.CircularLayoutManager
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.model.RelatedNode
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.apiDetailURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.deckKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.largeImageURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.nameKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.smallImageURLKey
import kotlinx.android.synthetic.main.fragment_creation.*

class CreationFragment : Fragment() {
    companion object {
        const val CENTER_NODE_RC = 1
        const val ADD_NODE_RC = 2
        fun newInstance(): CreationFragment {
            return CreationFragment()
        }
    }

    private val viewModel: CreationViewModel by activityViewModels()
    private lateinit var relatedNodesAdapter: RelatedNodesAdapter
    private var curNode = ComicNode()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_creation, container, false)

        initView(root)

        viewModel.observeRelatedNodes().observe(viewLifecycleOwner, Observer {
            relatedNodesAdapter.notifyDataSetChanged()
        })

        return root
    }

    private fun initView(root: View) {

        val relatedNodes_rv = root.findViewById<RecyclerView>(R.id.rv_relatedNodes)
        relatedNodesAdapter = RelatedNodesAdapter(viewModel)
        relatedNodes_rv.adapter = relatedNodesAdapter
//        relatedNodes_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        relatedNodes_rv.layoutManager = CircularLayoutManager(10f,10f)

        val addNode = root.findViewById<ImageButton>(R.id.addNode)
        val centerNode = root.findViewById<ImageView>(R.id.centerNodeImage)
        val clear = root.findViewById<Button>(R.id.clear)
        val save = root.findViewById<Button>(R.id.save)
        val customizedContent = root.findViewById<EditText>(R.id.customizedContent)

        addNode.setOnClickListener {
            injectComicNode(requireContext(), ADD_NODE_RC)
        }
        centerNode.setOnClickListener {
            injectComicNode(requireContext(), CENTER_NODE_RC)
        }
        clear.setOnClickListener {
            centerNode.setImageBitmap(null)
            curNode = ComicNode()

            viewModel.deleteAllRelatedNodes()
        }
        save.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null) {
                val text = "You have to log in first before saving any content."
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
            } else {
                curNode.userDescription = customizedContent.text.toString()

                viewModel.getUserComicNodes()
                viewModel.getGlobalComicNodes()
                val text = "Successfully saved."
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun injectComicNode(context: Context, requestCode: Int) {
        val intent = Intent(context, ComicNodeSearchActivity::class.java)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CENTER_NODE_RC -> {
                centerNodeImage.setBackgroundColor(Color.WHITE)
                data?.extras?.apply {
                    val name = getString(nameKey)
                    val deck = getString(deckKey)
                    val smallImageURL = getString(smallImageURLKey)
                    val largeImageURL = getString(largeImageURLKey)
                    val apiDetailURL = getString(apiDetailURLKey)

                    Glide.fetch(largeImageURL, largeImageURL, centerNodeImage)
                    centerNodeName.text = name
                    centerNodeDeck.text = deck
                    curNode.apply {
                        this.name = name
                        this.deck = deck
                        this.smallImageURL = smallImageURL
                        this.largeImageURL = largeImageURL
                        this.apiDetailURL = apiDetailURL
                    }
                }
            }
            ADD_NODE_RC -> {
                data?.extras?.apply {
                    val name = getString(nameKey)
                    val deck = getString(deckKey)
                    val smallImageURL = getString(smallImageURLKey)
                    val largeImageURL = getString(largeImageURLKey)
                    val apiDetailURL = getString(apiDetailURLKey)

                    val tempNode = ComicNode()
                    tempNode.apply {
                        this.name = name
                        this.deck = deck
                        this.smallImageURL = smallImageURL
                        this.largeImageURL = largeImageURL
                        this.apiDetailURL = apiDetailURL
                    }
                    viewModel.addRelatedNode(tempNode)
                }
            }
        }
    }
}