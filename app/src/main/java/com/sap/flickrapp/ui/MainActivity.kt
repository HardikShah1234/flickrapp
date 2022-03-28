package com.sap.flickrapp.ui

import android.database.sqlite.SQLiteCursor
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.sap.flickrapp.databinding.ActivityMainBinding
import com.sap.flickrapp.db.Adapter
import com.sap.flickrapp.db.SuggestionsDatabase
import com.sap.flickrapp.models.MainActivityViewModel
import com.sap.flickrapp.repository.NetworkState
import com.sap.flickrapp.repository.PhotoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    SearchView.OnSuggestionListener {

    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: SuggestionsDatabase
    private val adapter = PhotoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = SuggestionsDatabase(this)
        val gridLayoutManager = GridLayoutManager(this, 3)
        bind(binding, gridLayoutManager)
        search(DEFAULT_SEARCH_TERM)
    }

    //function for binding the views
    private fun bind(binding: ActivityMainBinding, gridLayoutManager: GridLayoutManager) {

        binding.apply {
            simpleSearchView.setOnQueryTextListener(this@MainActivity)
            simpleSearchView.setOnSuggestionListener(this@MainActivity)
            rvPhotos.setHasFixedSize(true)
            rvPhotos.adapter = adapter
            rvPhotos.layoutManager = gridLayoutManager
        }
        viewModel.photos.observe(this) {
            adapter.submitData(this.lifecycle, it)
        }

    }

    //search region
    private fun search(searchTerm: String) {
        viewModel.performSearch(searchTerm)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        if (query.isNotEmpty()) {
            binding.rvPhotos.scrollToPosition(0)
            adapter.submitData(this.lifecycle, PagingData.empty())
            search(query)
        }
        binding.simpleSearchView.clearFocus()

        val result = database.insertSuggestion(query)
        return result != -1L
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val cursor = database.getSuggestions(newText)
        return if (cursor.count != 0) {
            val columns = arrayOf(SuggestionsDatabase.FIELD_SUGGESTION)
            val columnTextId = intArrayOf(android.R.id.text1)
            val simple = Adapter(
                baseContext,
                android.R.layout.simple_list_item_1, cursor,
                columns, columnTextId, 0
            )
            binding.simpleSearchView.suggestionsAdapter = simple
            true
        } else {
            false
        }
    }

    override fun onSuggestionSelect(position: Int): Boolean {
        return false
    }

    override fun onSuggestionClick(position: Int): Boolean {
        val cursor = binding.simpleSearchView.suggestionsAdapter.getItem(position) as SQLiteCursor
        val indexColumnSuggestion = cursor.getColumnIndex(SuggestionsDatabase.FIELD_SUGGESTION)
        binding.simpleSearchView.setQuery(cursor.getString(indexColumnSuggestion), false)
        return true
    }
    //end region

    companion object {
        private const val DEFAULT_SEARCH_TERM = "Heidelberg"
    }
}