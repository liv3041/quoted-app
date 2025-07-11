package com.toonandtools.core_ui.data

import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName

data class User(
    @PropertyName("userName")
    val userName:String = "",
    @PropertyName("uuid")
    val uuid:String = "",
    @PropertyName("email")
    val email:String = "",
)

@IgnoreExtraProperties
data class Favorite(
    @PropertyName("storyID")
    val quoteID:String = "",
    @PropertyName("fav")
    val fav:Boolean = false
){
    constructor():this("",false)
}