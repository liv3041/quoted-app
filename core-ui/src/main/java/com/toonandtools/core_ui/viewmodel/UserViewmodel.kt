package com.toonandtools.core_ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.toonandtools.core_ui.repository.UserRepository

class UserViewmodel(private val userRepository: UserRepository):ViewModel() {



    fun addFavoriteItem(userID: String, quoteId: String,isFavorite:Boolean){
        userRepository.addFavoriteItem(userID, quoteId, isFavorite = isFavorite)
    }
    fun removeFavorite(userID: String, quoteId: String) {
        userRepository.removeFavorite(userID, quoteId)
    }



    fun isFavorite(userID: String,quoteId: String): LiveData<Boolean> {
        return    userRepository.isFavorite(userID, quoteId)

    }
}