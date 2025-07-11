package com.toonandtools.core_ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.toonandtools.core_ui.data.QuoteItem
import com.toonandtools.core_ui.repository.FirestoreDB

class FirestoreViewmodel(private val firestoreDB: FirestoreDB): ViewModel() {

    fun fetchQuoteItem(quoteId:String): LiveData<List<QuoteItem>> {
        return firestoreDB.fetchQuoteById(quoteId)
    }
    fun fetchAllQuotes(): LiveData<List<QuoteItem>> {
        return firestoreDB.fetchAllQuotes()
    }

}