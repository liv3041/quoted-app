package com.toonandtools.core_ui.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.toonandtools.core_ui.data.QuoteItem

class FirestoreDB {
    private val firestore = FirebaseFirestore.getInstance()
    private val quotes = firestore.collection("quotes")


    fun fetchQuoteById(quoteId:String): LiveData<List<QuoteItem>> {
        val quoteLiveData = MutableLiveData<List<QuoteItem>>()

        val quoteRef = quotes.document(quoteId)

        quoteRef.get()
            .addOnSuccessListener { doc->
                val quoteItem = doc.toObject(QuoteItem::class.java)
                if (quoteItem != null) {
                    quoteLiveData.value = listOf(quoteItem)
                } else {
                    quoteLiveData.value = emptyList()
                }
            }.addOnFailureListener { exception->
                quoteLiveData.value = emptyList()

            }

        return quoteLiveData

    }
    fun fetchAllQuotes(): LiveData<List<QuoteItem>> {
        val liveData = MutableLiveData<List<QuoteItem>>()

        quotes.get()
            .addOnSuccessListener { result ->
                val items = result.mapNotNull { it.toObject(QuoteItem::class.java) }
                Log.d("FirestoreData", "Fetched ${items.size} quotes")

                liveData.value = items
            }
            .addOnFailureListener {
                liveData.value = emptyList()
            }

        return liveData
    }


}