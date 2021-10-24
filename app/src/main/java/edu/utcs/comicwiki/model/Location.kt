package edu.utcs.comicwiki.model

import com.google.gson.annotations.SerializedName

data class Location(
    // unique info
    @SerializedName("count_of_issue_appearances")
    val countOfIssueAppearances: Int,
    @SerializedName("first_appeared_in_issue")
    val firstAppearedInIssue: Issue,
    @SerializedName("start_year")
    val startYear: Int,
    @SerializedName("issue_credits")
    val issue_credits: List<Issue>,
    @SerializedName("story_arc_credits")
    val storyArcCredits: List<StoryArc>,
    @SerializedName("volume_credits")
    val volumeCredits: List<Volume>,

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