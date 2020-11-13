package com.wcc.todayscocktail.view

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.wcc.todayscocktail.R
import com.wcc.todayscocktail.data.CocktailDatabase
import com.wcc.todayscocktail.network.CocktailsApi
import com.wcc.todayscocktail.repository.CocktailsListRepository
import com.wcc.todayscocktail.viewmodel.CocktailsListViewModel
import com.wcc.todayscocktail.viewmodel.CocktailsListViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseDao = CocktailDatabase.getDatabase(this, CoroutineScope(Dispatchers.IO)).cocktailDao()
        val remoteService = CocktailsApi.retrofitService
        val repository = CocktailsListRepository(databaseDao, remoteService)

        val viewModelFactory = CocktailsListViewModelFactory(repository)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(CocktailsListViewModel::class.java)
        val list = viewModel.cocktailList

        val recyclerView = findViewById<RecyclerView>(R.id.cocktailsListRecyclerView)
        val adapter = CocktailsListAdapter()
        recyclerView.adapter = adapter

        list.observe(this, Observer {
            adapter.data = it
            recyclerView.visibility = View.VISIBLE
            findViewById<ProgressBar>(R.id.loadingDrinksIndicator).visibility = View.GONE
        })
    }
}