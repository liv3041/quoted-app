package com.toonandtools.core_ui.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.toonandtools.core_ui.data.Favorite
import com.toonandtools.core_ui.data.User

class UserRepository {
    private lateinit var  databaseRef : DatabaseReference
    private lateinit var uuid:String
    private val database = FirebaseDatabase.getInstance().reference
    private val TAG:String="USER REPOSITORY"



    fun isFavorite(userID: String,quoteId: String): LiveData<Boolean> {

        val isFavoriteLiveData = MutableLiveData<Boolean>()
        val userFavorite = database.child("users").child(userID).child("Favorite").child(quoteId)
        userFavorite.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isFavoriteLiveData.value = snapshot.exists()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to check bookmark status: ${error.message}")
            }
        })
        return isFavoriteLiveData
    }
    fun removeFavorite(userID: String, favoriteID: String) {
        database.child("users").child(userID).child("Favorite").child(favoriteID).removeValue().addOnCompleteListener {
            Log.d(TAG,"Success:Bookmark Data removed from database")
        }.addOnFailureListener {
            Log.e(TAG,"Failure:Bookmark data not removed ${it.message}")
        }
    }

    fun addFavoriteItem(userID: String, quoteId: String, isFavorite: Boolean){
        val favorite = Favorite(quoteId,isFavorite)
        val userFavoritesRef = database.child("users").child(userID).child("Favorite")
        userFavoritesRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.childrenCount>=maxLimit){
//                    Log.d(TAG,"Favorite Items Exceed the limit")
//                }else{
                    val newItem = userFavoritesRef.child(quoteId)
                    newItem.setValue(favorite)
//                }

//                userFavoritesRef.setValue(bookMark)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to add Bookmark: ${error.message}")
            }
        })
    }
}