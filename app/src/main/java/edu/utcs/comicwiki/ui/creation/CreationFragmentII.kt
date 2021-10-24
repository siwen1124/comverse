package edu.utcs.comicwiki.ui.creation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.kapil.circularlayoutmanager.CircularLayoutManager
import edu.utcs.comicwiki.MainActivity
import edu.utcs.comicwiki.R
import edu.utcs.comicwiki.glide.Glide
import edu.utcs.comicwiki.model.ComicNode
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.apiDetailURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.deckKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.largeImageURLKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.nameKey
import edu.utcs.comicwiki.ui.creation.ComicNodeSearchActivity.Companion.smallImageURLKey
import kotlinx.android.synthetic.main.fragment_test.*


class CreationFragmentII : Fragment(R.layout.fragment_test) {
    companion object {
        const val CENTER_NODE_RC = 1
        const val RELATED_NODE_RC = 2
        fun newInstance(): CreationFragmentII {
            return CreationFragmentII()
        }
    }

    private val viewModel: CreationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        initializeActions()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        rv.adapter = RecyclerViewAdapter(viewModel)
        rv.addItemDecoration(RecyclerItemDecoration())
    }

    private fun initializeObservers() {
        viewModel.observeRelatedNodes().observe(viewLifecycleOwner, Observer {
            rv.adapter?.notifyDataSetChanged()
        })
        viewModel.observeCenterNode().observe(viewLifecycleOwner, Observer {
            centerNodeImage.background = null
            Glide.fetch(it.largeImageURL, it.largeImageURL, centerNodeImage)
            centerNodeImage.clipToOutline = true
            centerNodeImage.imageAlpha = 100
            centerNodeName.text = it.name
        })
        viewModel.observeUserDescription().observe(viewLifecycleOwner, Observer {
            customizedContent.setText(it, TextView.BufferType.EDITABLE)
        })
    }

    private fun initializeActions() {
        addRelatedNode.setOnClickListener {
            injectComicNode(requireContext(), RELATED_NODE_RC)
        }
        addCenterNode.setOnClickListener {
            injectComicNode(requireContext(), CENTER_NODE_RC)
        }
        centerNodeImage.setOnClickListener {
        }
        clearAll.setOnClickListener {
            viewModel.deleteAllRelatedNodes()
            viewModel.deleteCenterNode()
            viewModel.deleteUserDescription()
        }
        save.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser == null) {
                val text = "You have to log in first before saving any content."
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (viewModel.observeCenterNode().value == null) {
                val text = "You have to create center node first before saving any content."
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.setUserDescription(customizedContent.text.toString())
            viewModel.saveComicNode()
            val text = "Successfully saved."
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
        customizedContent.addTextChangedListener(object : TextWatcher {
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
                if (s.isEmpty()) {
                    (context as MainActivity).hideKeyboard()
                }
            }
        })
    }

    private fun injectComicNode(context: Context, requestCode: Int) {
        val intent = Intent(context, ComicNodeSearchActivity::class.java)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CENTER_NODE_RC -> {
                data?.extras?.apply {
                    val name = getString(nameKey)
                    val deck = getString(deckKey)
                    val smallImageURL = getString(smallImageURLKey)
                    val largeImageURL = getString(largeImageURLKey)
                    val apiDetailURL = getString(apiDetailURLKey)

                    val tempCenterNode = ComicNode()
                    tempCenterNode.apply {
                        this.name = name ?: ""
                        this.deck = deck
                        this.smallImageURL = smallImageURL ?: ""
                        this.largeImageURL = largeImageURL ?: ""
                        this.apiDetailURL = apiDetailURL ?: ""
                    }

                    viewModel.setCenterNode(tempCenterNode)

                    Toast.makeText(context, "Center node created", Toast.LENGTH_SHORT).show()
                }
            }
            RELATED_NODE_RC -> {
                data?.extras?.apply {
                    val name = getString(nameKey)
                    val deck = getString(deckKey)
                    val smallImageURL = getString(smallImageURLKey)
                    val largeImageURL = getString(largeImageURLKey)
                    val apiDetailURL = getString(apiDetailURLKey)

                    val tempRelatedNode = ComicNode()
                    tempRelatedNode.apply {
                        this.name = name ?: ""
                        this.deck = deck
                        this.smallImageURL = smallImageURL ?: ""
                        this.largeImageURL = largeImageURL ?: ""
                        this.apiDetailURL = apiDetailURL ?: ""
                    }
                    viewModel.addRelatedNode(tempRelatedNode)

                    Toast.makeText(context, "Related node created", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}