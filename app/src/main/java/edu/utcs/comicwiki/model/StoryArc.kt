package edu.utcs.comicwiki.model

import com.google.gson.annotations.SerializedName

data class StoryArc(
    // unique info
    @SerializedName("issues")
    val issues: List<Issue>,
    @SerializedName("first_appeared_in_issue")
    val firstAppearedInIssue: Issue,
    @SerializedName("count_of_issue_appearances")
    val countOfIssueAppearances: Int,

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
