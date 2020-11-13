package com.wcc.todayscocktail.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wcc.todayscocktail.data.CocktailDAO
import com.wcc.todayscocktail.entity.Cocktail
import com.wcc.todayscocktail.network.CocktailsApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CocktailsListRepository(private val cocktailDao: CocktailDAO,
                              private val cocktailApi: CocktailsApiService) {
    private val cocktailListResponse = MutableLiveData<List<Cocktail>>()

    val cocktailsList: LiveData<List<Cocktail>>
        get() = cocktailListResponse

    init {
        getCocktails()
    }

    private fun getCocktails() {
        CoroutineScope(Dispatchers.IO).launch {
            val listFromDB = cocktailDao.getAll()
            if(listFromDB.isNotEmpty()) {
                cocktailListResponse.postValue(listFromDB)
            } else {
                getCocktailsFromRemote()
            }
        }
    }

    private fun getCocktailsFromRemote() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val listResult = cocktailApi.getCocktails().cocktailsList
                saveRemoteDataAtDatabase(listResult)
                cocktailListResponse.postValue(listResult)

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    cocktailListResponse.postValue(listOf())
                }//todo: improve remote access error
            }
        }
    }

    private fun saveRemoteDataAtDatabase(cocktailList: List<Cocktail>) {
        CoroutineScope(Dispatchers.IO).launch {
            for(cocktail in cocktailList) {
                cocktailDao.insert(cocktail)
            }
        }
    }

}
