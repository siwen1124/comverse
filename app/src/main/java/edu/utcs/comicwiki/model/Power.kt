package edu.utcs.comicwiki.model

import com.google.gson.annotations.SerializedName

data class Power(
    // unique info
    @SerializedName("characters")
    val characters: List<Character>,

    // standard info
    @SerializedName("name")
    val name: String,
    @SerializedName("aliases")
    val aliases: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: Image,
    @SerializedName("api_detail_url")
    val apiDetailURL: String,
    @SerializedName("site_detail_url")
    val siteDetailURL: String
)


