package com.toonandtools.auth.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.toonandtools.auth.R
import com.toonandtools.auth.repository.AuthRepository
import kotlinx.coroutines.launch
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = application.applicationContext
    private val repository = AuthRepository(FirebaseAuth.getInstance())

    fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    fun signInWithGoogle(account: GoogleSignInAccount, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.firebaseAuthWithGoogle(account)
            onResult(result is com.toonandtools.auth.util.AuthResult.Success)
        }
    }
}