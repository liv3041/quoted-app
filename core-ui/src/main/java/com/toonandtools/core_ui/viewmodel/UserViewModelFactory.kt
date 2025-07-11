package com.toonandtools.core_ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.toonandtools.core_ui.repository.UserRepository

class UserViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewmodel::class.java)){
            return UserViewmodel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }

}