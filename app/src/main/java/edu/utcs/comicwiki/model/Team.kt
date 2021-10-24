package edu.utcs.comicwiki.model

import com.google.gson.annotations.SerializedName

data class Team(
    // unique info
    @SerializedName("issue_credits")
    val issueCredits: List<Issue>,
    @SerializedName("issues_disbanded_in")
    val issuesDisbandedIn: List<Issue>,
    @SerializedName("story_arc_credits")
    val storyArcCredits: List<StoryArc>,
    @SerializedName("volume_credits")
    val volumeCredits: List<Volume>,
    @SerializedName("characters")
    val characters: List<Character>,
    @SerializedName("character_friends")
    val characterFriends: List<Character>,
    @SerializedName("character_enemies")
    val characterEnemies: List<Character>,
    @SerializedName("count_of_issue_appearances")
    val countOfIssueAppearances: Int,
    @SerializedName("first_appeared_in_issue")
    val firstAppearedInIssue: Issue,
    @SerializedName("count_of_team_members")
    val countOfTeamMembers: Int,

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

