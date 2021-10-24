package edu.utcs.comicwiki.model

import com.google.gson.annotations.SerializedName

data class Issue(
    // unique info
    @SerializedName("first_appearance_characters")
    val first_appearance_characters: List<Character>,
    @SerializedName("first_appearance_concepts")
    val firstAppearanceConcepts: List<Concept>,
    @SerializedName("first_appearance_locations")
    val firstAppearanceLocations: List<Location>,
    @SerializedName("first_appearance_objects")
    val firstAppearanceObjects: List<Object>,
    @SerializedName("first_appearance_teams")
    val firstAppearanceTeams: List<Team>,
    @SerializedName("teams_disbanded_in")
    val teamsDisbandedIn: List<Team>,
    @SerializedName("characters_died_in")
    val charactersDiedIn: List<Character>,
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
    @SerializedName("story_arc_credits")
    val storyArcCredits: List<StoryArc>,

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