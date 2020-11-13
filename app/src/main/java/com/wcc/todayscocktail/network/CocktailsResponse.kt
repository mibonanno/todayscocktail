package com.wcc.todayscocktail.network

import com.squareup.moshi.Json
import com.wcc.todayscocktail.entity.Cocktail

data class CocktailsResponse (
        @Json(name = "drinks")
        val cocktailsList: List<Cocktail>)