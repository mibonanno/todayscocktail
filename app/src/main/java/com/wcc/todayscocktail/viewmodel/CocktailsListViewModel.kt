package com.wcc.todayscocktail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wcc.todayscocktail.entity.Cocktail
import com.wcc.todayscocktail.repository.CocktailsListRepository

class CocktailsListViewModel(private val repository: CocktailsListRepository): ViewModel() {

    val  cocktailList: LiveData<List<Cocktail>>
        get() = repository.cocktailsList

}