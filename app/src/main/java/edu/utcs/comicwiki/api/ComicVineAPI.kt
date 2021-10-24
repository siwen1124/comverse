package edu.utcs.comicwiki.api

import edu.utcs.comicwiki.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicVineAPI {

    var apiKey: String
        get() = "b685154d28f6b3fc55b27a06dfaed34041028bd2"
        set(value) = TODO()

    @GET("characters/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchGenericItem(): GenericItemsResponse

    @GET("characters/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchCharacters(): CharactersResponse

    @GET("powers/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json")
    suspend fun fetchPowers(): PowersResponse

    @GET("teams/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchTeams(): TeamsResponse

    @GET("locations/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchLocations(): LocationsResponse

    @GET("objects/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchObjects(): ObjectsResponse

    @GET("concepts/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchConcepts(): ConceptsResponse

    @GET("{characterPath}/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=20")
    suspend fun fetchCharacterFromPath(@Path("characterPath") characterPath: String?): CharacterResponse

    @GET("{teamPath}/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json")
    suspend fun fetchTeam(@Path("teamPath") team_apiPath: String?): TeamResponse

    @GET("search/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=5&resources=character")
    suspend fun searchCharacter(@Query("query") keyWord: String): CharactersResponse

    @GET("search/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&resources=character")
    suspend fun searchCharacters(@Query("query") keyWords: String): CharactersResponse

    @GET("search/?api_key=b685154d28f6b3fc55b27a06dfaed34041028bd2&format=json&limit=21")
    suspend fun search(
        @Query("query") query: String,
        @Query("resources") resources: String
    ): GenericItemsResponse

    class GenericItemsResponse(val results: List<GenericItem>)
    class CharacterResponse(val results: Character)
    class CharactersResponse(val results: List<Character>)
    class PowersResponse(val results: List<Power>)
    class TeamsResponse(val results: List<Team>)
    class ObjectsResponse(val results: List<Object>)
    class LocationsResponse(val results: List<Location>)
    class ConceptsResponse(val results: List<Concept>)
    class TeamResponse(val results: Team)

    companion object Factory {
        fun create(): ComicVineAPI {
            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://comicvine.gamespot.com/api/")
                .build()
            return retrofit.create(ComicVineAPI::class.java)
        }
    }
}