package com.toonandtools.core_ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toonandtools.core_ui.repository.FirestoreDB

class FirestoreViewModelFactory(private val repository: FirestoreDB): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirestoreViewmodel::class.java)) {
            return FirestoreViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}