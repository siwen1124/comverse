package edu.utcs.comicwiki.ui.home

import androidx.lifecycle.*
import edu.utcs.comicwiki.api.ComicVineAPI
import edu.utcs.comicwiki.api.ComicVineRepo
import edu.utcs.comicwiki.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel : ViewModel() {
    companion object {
        fun URL2Path(str: String): String {
            return str.substringAfter("api/").substringBeforeLast("/")
        }
    }

    private val comicVineAPI = ComicVineAPI.create()
    private val comicVineRepo = ComicVineRepo(comicVineAPI)

    private val characters = MutableLiveData<List<Character>>()
    private val teams = MutableLiveData<List<Team>>()
    private val powers = MutableLiveData<List<Power>>()
    private val locations = MutableLiveData<List<Location>>()
    private val concepts = MutableLiveData<List<Concept>>()
    private val objects = MutableLiveData<List<Object>>()
    private val randomItem = MediatorLiveData<Character>()


    private val team_apiPath = MutableLiveData<String>()
    private val team = MutableLiveData<Team>()
    private val teamMembers = MutableLiveData<List<Character>>()
    private val teamFriends = MutableLiveData<List<Character>>()
    private val teamEnemies = MutableLiveData<List<Character>>()

    init {
        netFetchCharacters()
        netFetchTeams()
        netFetchPowers()
        netFetchLocations()
        netFetchConcepts()
        netFetchObjects()
        netFetchRandomItem()

        netFetchRandomItem()
        randomItem.addSource(characters) {
            val random = Random.nextInt(0, characters.value?.size ?: 0)
            randomItem.value = characters.value?.get(random)
        }
    }

    // region: randomItem
    fun netFetchRandomItem() {
//        randomItem.addSource(characters) {
//            val random = Random.nextInt(0, characters.value?.size ?: 0)
//            randomItem.value = characters.value?.get(random)
//        }
    }

    fun observeRandomItem(): LiveData<Character> {
        return randomItem
    }

    fun getRandomItem(): Character? {
        return randomItem.value
    }

    // endregion


    // region: characterList
    fun netFetchCharacters() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        characters.postValue(comicVineRepo.fetchCharacters())
    }

    fun observeCharacters(): LiveData<List<Character>> {
        return characters
    }

    fun getCharactersAt(position: Int): Character? {
        val localList = characters.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getCharacterListCount(): Int {
        return characters.value?.size ?: 0
    }
    // endregion

    // region: teamList
    fun netFetchTeams() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        teams.postValue(comicVineRepo.fetchTeams())
    }

    fun observeTeams(): LiveData<List<Team>> {
        return teams
    }

    fun getTeamsAt(position: Int): Team? {
        val localList = teams.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getTeamListCount(): Int {
        return teams.value?.size ?: 0
    }
    // endregion

    // region: powers
    fun netFetchPowers() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        powers.postValue(comicVineRepo.fetchPowers())
    }

    fun observePowers(): LiveData<List<Power>> {
        return powers
    }

    fun getPowersAt(position: Int): Power? {
        val localList = powers.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getPowersCount(): Int {
        return powers.value?.size ?: 0
    }
    // endregion

    // region: locations
    fun netFetchLocations() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        locations.postValue(comicVineRepo.fetchLocations())
    }

    fun observeLocations(): LiveData<List<Location>> {
        return locations
    }

    fun getLocationsAt(position: Int): Location? {
        val localList = locations.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getLocationsCount(): Int {
        return locations.value?.size ?: 0
    }
    // endregion

    // region: concepts
    fun netFetchConcepts() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        concepts.postValue(comicVineRepo.fetchConcepts())
    }

    fun observeConcepts(): LiveData<List<Concept>> {
        return concepts
    }

    fun getConceptsAt(position: Int): Concept? {
        val localList = concepts.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getConceptsCount(): Int {
        return concepts.value?.size ?: 0
    }

    // endregion

    // region: objects

    fun netFetchObjects() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        objects.postValue(comicVineRepo.fetchObjects())
    }

    fun observeObjects(): LiveData<List<Object>> {
        return objects
    }

    fun getObjectsAt(position: Int): Object? {
        val localList = objects.value?.toList()
        localList?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getObjectsCount(): Int {
        return objects.value?.size ?: 0
    }

    // endregion

    // region: single team
    fun netFetchTeam() = viewModelScope.launch(
        context = viewModelScope.coroutineContext
                + Dispatchers.IO
    ) {
        team.postValue(comicVineRepo.fetchTeam(team_apiPath.value))
    }

    fun observeTeam(): LiveData<Team> {
        return team
    }

    fun set_team_apiPath(apiDetailURL: String) {
        team_apiPath.value = URL2Path(apiDetailURL)
    }

    fun getTeamMemberAt(position: Int): Character? {
        val local = teamMembers.value?.toList()
        local?.let {
            if (position >= it.size)
                return null
            return it[position]
        }
        return null
    }

    fun getTeamMembersCount(): Int {
        return teamMembers.value?.size ?: 0
    }
    // endregion


    // region: implements teamMembers
    fun netFetch_teamMembers() {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            val limit = 10
            val characterList = team.value?.characters
            val result = mutableListOf<Character>()

            if (characterList.isNullOrEmpty())
                return@launch

            if (characterList.size > limit) {
                val paths = mutableListOf<String>()
                for (i in 0..limit)
                    paths.add(URL2Path(characterList[i].apiDetailURL))
                for (path in paths)
                    result.add(comicVineRepo.fetCharacterFromPath(path))
            } else {
                val paths = characterList.map {
                    URL2Path(it.apiDetailURL)
                }
                for (path in paths)
                    result.add(comicVineRepo.fetCharacterFromPath(path))
            }

            teamMembers.postValue(result)
        }
    }

    fun observeTeamMembers(): LiveData<List<Character>> {
        return teamMembers
    }

    fun netFetch_teamFriends() {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            val names = team.value?.characterFriends?.map {
                it.name
            }
            teamFriends.postValue(comicVineRepo.fetchTeamMembers(names))
        }
    }

    fun observeTeamFriends(): LiveData<List<Character>> {
        return teamFriends
    }

    fun netFetch_teamEnemies() {
        viewModelScope.launch(
            context = viewModelScope.coroutineContext
                    + Dispatchers.IO
        ) {
            val names = team.value?.characterEnemies?.map {
                it.name
            }
            teamEnemies.postValue(comicVineRepo.fetchTeamMembers(names))
        }
    }

    fun observeTeamEnemies(): LiveData<List<Character>> {
        return teamEnemies
    }
    // endregion

}