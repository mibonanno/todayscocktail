package com.wcc.todayscocktail.network

import androidx.lifecycle.LiveData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CocktailsApiService {
    @GET( "filter.php?a=Alcoholic")
    suspend fun getCocktails(): CocktailsResponse
}

object CocktailsApi {
    val retrofitService: CocktailsApiService by lazy {
        retrofit.create(CocktailsApiService::class.java)
    }
}