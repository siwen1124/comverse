package edu.utcs.comicwiki.api

import edu.utcs.comicwiki.model.*

class ComicVineRepo(private val comicVineAPI: ComicVineAPI) {
    // simple fetch
    suspend fun fetchCharacters(): List<Character> {
        return comicVineAPI.fetchCharacters().results
    }

    suspend fun fetchPowers(): List<Power> {
        return comicVineAPI.fetchPowers().results
    }

    suspend fun fetchTeams(): List<Team> {
        return comicVineAPI.fetchTeams().results
    }

    suspend fun fetchLocations(): List<Location> {
        return comicVineAPI.fetchLocations().results
    }

    suspend fun fetchConcepts(): List<Concept> {
        return comicVineAPI.fetchConcepts().results
    }

    suspend fun fetchObjects(): List<Object> {
        return comicVineAPI.fetchObjects().results
    }




    suspend fun fetch(): List<Character> {
        return comicVineAPI.fetchCharacters().results
    }

    suspend fun fetchTeam(team_apiPath: String?): Team {
        return comicVineAPI.fetchTeam(team_apiPath).results
    }


    suspend fun fetCharacterFromPath(characterPath: String?): Character {
        return comicVineAPI.fetchCharacterFromPath(characterPath).results
    }
    
    suspend fun searchCharacters(keyWord: String): List<Character>? {
        return comicVineAPI.searchCharacter(keyWord).results
    }



    suspend fun fetchTeamMembers(characterList: List<String>?): List<Character>? {
        return comicVineAPI.searchCharacters("spider-man").results
    }


    suspend fun search(query: String, resources: String): List<GenericItem> {
        return comicVineAPI.search(query, resources).results
    }
}