package edu.utcs.comicwiki.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class ComicNode(
    // from firebase
    var selfID: String = "",
    var ownerUID: String = "",
//    @ServerTimestamp var timeStamp: Timestamp? = null,

    // from user
    var relatedNodes: List<ComicNode>? = null,
    var userDescription: String? = "",

    // from api
    var name: String = "",
    var deck: String? = null,
    var smallImageURL: String = "",
    var largeImageURL: String = "",
    var apiDetailURL: String = ""
)