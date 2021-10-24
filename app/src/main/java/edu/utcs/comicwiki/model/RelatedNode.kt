package edu.utcs.comicwiki.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class RelatedNode(
    // from firebase
    var selfID: String = "",
    var ownerUID: String = "",

    // from user
    var relatedNodes: List<RelatedNode>? = null,
    var userDescription: String? = "",

    // from api
    var name: String = "",
    var deck: String? = null,
    var smallImageURL: String = "",
    var largeImageURL: String = "",
    var apiDetailURL: String = ""
)