package com.toonandtools.auth.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.toonandtools.core_ui.data.User

class UserRepository {
    private lateinit var  databaseRef : DatabaseReference
    private lateinit var uuid:String
    private val database = FirebaseDatabase.getInstance().reference
    private val TAG:String="USER REPOSITORY"

    fun saveUserData(email:String,displayName:String) {
        val database = FirebaseDatabase.getInstance()
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            uuid = firebaseUser.uid
            val username = firebaseUser.displayName.toString()
            Log.d(TAG,"$username")
            val user = User(uuid = uuid, userName = displayName,email= email)
            databaseRef = database.getReference("users").child(uuid)

            databaseRef.setValue(user)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "User data saved successfully")
                    } else {
                        Log.e(TAG, "Failed to save user data: ${it.exception}")
                    }
                }
        }
    }
}