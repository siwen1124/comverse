package edu.utcs.comicwiki.model

import com.google.gson.annotations.SerializedName

data class GenericItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("deck")
    val deck: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: Image,
    @SerializedName("api_detail_url")
    val apiDetailURL: String,
    @SerializedName("site_detail_url")
    val siteDetailURL: String
)