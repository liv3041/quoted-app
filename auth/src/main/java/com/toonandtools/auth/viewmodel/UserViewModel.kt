package com.toonandtools.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.toonandtools.auth.repository.UserRepository

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun saveUserData(email:String,displayName:String){
        userRepository.saveUserData(email,displayName)
    }
}