package edu.utcs.comicwiki.ui.creation

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.utcs.comicwiki.model.ComicNode

class CreationViewModel() : ViewModel() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val centerNode = MutableLiveData<ComicNode>().apply {
        value = ComicNode()
    }
    private val relatedNodes = MutableLiveData<List<ComicNode>>().apply {
        value = mutableListOf()
    }
    private val userDescription = MutableLiveData<String>().apply {
        value = ""
    }

    private val globalComicNodes = MutableLiveData<List<ComicNode>>()
    private val userComicNode = MutableLiveData<List<ComicNode>>().apply {
        value = mutableListOf()
    }

    init {
        getGlobalComicNodes()
        getUserComicNodes()
    }

    // region: userDescription
    fun observeUserDescription(): LiveData<String> {
        return userDescription
    }

    fun deleteUserDescription() {
        userDescription.value = ""
    }

    fun setUserDescription(str: String?) {
        userDescription.value = str
    }

    fun getUserDescription(): String {
        return userDescription.value ?: ""
    }
    // endregion

    // region: centerComicNode
    fun observeCenterNode(): LiveData<ComicNode> {
        return centerNode
    }

    fun deleteCenterNode() {
        centerNode.value = ComicNode()
    }

    fun setCenterNode(comicNode: ComicNode) {
        centerNode.value = comicNode
    }
    // endregion

    // region: relatedComicNodes
    fun observeRelatedNodes(): LiveData<List<ComicNode>> {
        return relatedNodes
    }

    fun addRelatedNode(relatedNode: ComicNode) {
        val local = relatedNodes.value?.toMutableList()
        local?.let {
            it.add(relatedNode)
            relatedNodes.value = it
        }
    }

    fun getRelatedNodesAt(position: Int): ComicNode? {
        val local = relatedNodes.value?.toList()
        local?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getRelatedNodesCount(): Int {
        return relatedNodes.value?.size ?: 0
    }

    fun deleteRelatedNodesAt(position: Int) {
        val local = relatedNodes.value?.toMutableList()
        local?.let {
            it.removeAt(position)
            relatedNodes.value = it
        }
    }

    fun deleteAllRelatedNodes() {
        relatedNodes.value = mutableListOf<ComicNode>()
    }

    fun getAllRelatedNodes(): MutableList<ComicNode>? {
        return relatedNodes.value?.toMutableList()
    }

    fun setRelatedNodes(items: List<ComicNode>?) {
        relatedNodes.value = items
    }
    // endregion

    // region: global comic nodes
    fun getGlobalComicNodes() {
        db.collection("globalComicNodes")
//            .orderBy("timeStamp")
            .limit(50)
            .addSnapshotListener { querySnapshot, ex ->
                if (ex != null) {
                    return@addSnapshotListener
                }
                globalComicNodes.value = querySnapshot?.documents?.mapNotNull {
                    it.toObject(ComicNode::class.java)
                }
            }
    }

    fun observeGlobalComicNodes(): LiveData<List<ComicNode>> {
        return globalComicNodes
    }

    fun getGlobalComicNodesAt(position: Int): ComicNode? {
        val local = globalComicNodes.value?.toList()
        local?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getGlobalComicNodesCount(): Int {
        return globalComicNodes.value?.size ?: 0
    }
    // endregion

    // region: user comic nodes
    fun getUserComicNodes() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            userComicNode.value = mutableListOf()
            return
        }
        db.collection("globalComicNodes")
            .whereEqualTo("ownerUID", FirebaseAuth.getInstance().currentUser!!.uid)
//            .orderBy("timeStamp")
            .limit(50)
            .addSnapshotListener { querySnapshot, ex ->
                if (ex != null) {
                    return@addSnapshotListener
                }
                userComicNode.value = querySnapshot?.documents?.mapNotNull {
                    it.toObject(ComicNode::class.java)
                }
            }
    }

    fun observeUserComicNodes(): LiveData<List<ComicNode>> {
        return userComicNode
    }

    fun getUserComicNodesAt(position: Int): ComicNode? {
        val local = userComicNode.value?.toList()
        local?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getUserComicNodesCount(): Int {
        return userComicNode.value?.size ?: 0
    }
    // endregion

    // region: data change
    fun saveComicNode() {
        // inject ids
        if (centerNode.value == null)
            return

        if (relatedNodes.value != null)
            centerNode.value!!.relatedNodes = relatedNodes.value

        if (userDescription.value != null)
            centerNode.value!!.userDescription = userDescription.value

        centerNode.value!!.selfID = db.collection("globalComicNodes").document().id
        centerNode.value!!.ownerUID = FirebaseAuth.getInstance().currentUser!!.uid

        // create document
        db.collection("globalComicNodes")
            .document(centerNode.value!!.selfID)
            .set(centerNode.value!!)
            .addOnSuccessListener {
                getGlobalComicNodes()
                getUserComicNodes()
            }
            .addOnFailureListener { e ->
                Log.d(javaClass.simpleName, e.message)
            }
    }

    fun updateComicNode(comicNode: ComicNode) {
        db.collection("globalComicNodes")
            .document(comicNode.selfID)
            .set(comicNode)
            .addOnSuccessListener {
                getGlobalComicNodes()
                getUserComicNodes()
            }
            .addOnFailureListener { e ->
                Log.d(javaClass.simpleName, e.message)
            }
    }

    fun deleteComicNode(comicNode: ComicNode?) {
        if (comicNode == null)
            return

        db.collection("globalComicNodes")
            .document(comicNode.selfID)
            .delete()
            .addOnSuccessListener {
            }
            .addOnFailureListener { e ->
                Log.d(javaClass.simpleName, e.message)
            }
    }

    fun moveComicNode(from: Int, to: Int) {
//        if (from == to) return
//        val local = userComicNode.value?.toMutableList()
//        local?.let {
//            // swap order info
//            deleteComicNode(it[from])
//            deleteComicNode(it[to])
//            val temp = it[from].timeStamp
//            it[from].timeStamp = it[to].timeStamp
//            it[to].timeStamp = temp
//            updateComicNode(it[from])
//            updateComicNode(it[to])
//
//            // swap location in list
//            val fromComicNode = it[from]
//            it.removeAt(from)
//            if (to < from) {
//                it.add(to, fromComicNode)
//            } else {
//                it.add(to - 1, fromComicNode)
//            }
//        }
    }
    // endregion
}