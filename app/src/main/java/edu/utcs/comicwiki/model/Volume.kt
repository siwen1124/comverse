package edu.utcs.comicwiki.model

import com.google.gson.annotations.SerializedName

data class Volume(
    // unique info
    @SerializedName("issues")
    val issues: List<Issue>,
    @SerializedName("start_year")
    val startYear: Int,
    @SerializedName("count_of_issues")
    val countOfIssues: Int,
    @SerializedName("location_credits")
    val locationCredits: List<Location>,
    @SerializedName("object_credits")
    val objectCredits: List<Object>,
    @SerializedName("team_credits")
    val teamCredits: List<Team>,
    @SerializedName("concept_credits")
    val conceptCredits: List<Concept>,
    @SerializedName("character_credits")
    val characterCredits: List<Character>,

    // standard info
    @SerializedName("name")
    val name: String,
    @SerializedName("aliases")
    val aliases: String,
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