package edu.utcs.comicwiki.model

import com.google.gson.annotations.SerializedName

data class Character(
    // unique info
    @SerializedName("birth")
    val birth: String,
    @SerializedName("character_enemies")
    val characterEnemies: List<Character>,
    @SerializedName("character_friends")
    val characterFriends: List<Character>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("real_name")
    val realName: String,
    @SerializedName("powers")
    val powers: List<Power>,
    @SerializedName("count_of_issue_appearances")
    val countOfIssueAppearances: Int,
    @SerializedName("first_appeared_in_issue")
    val firstAppearedInIssue: Issue,
    @SerializedName("start_year")
    val startYear: Int,
    @SerializedName("issue_credits")
    val issueCredits: List<Issue>,
    @SerializedName("issues_died_in")
    val issuesDiedIn: List<Issue>,
    @SerializedName("story_arc_credits")
    val storyArcCredits: List<StoryArc>,
    @SerializedName("volume_credits")
    val volumeCredits: List<Volume>,
    @SerializedName("team_enemies")
    val teamEnemies: List<Team>,
    @SerializedName("team_friends")
    val teamFriends: List<Team>,
    @SerializedName("teams")
    val teams: List<Team>,

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

