package com.toonandtools.auth.repository



import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.toonandtools.auth.util.AuthResult
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth) {

    suspend fun login(email: String, password: String): AuthResult {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Login failed")
        }
    }

    suspend fun register(email: String, password: String): AuthResult {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Failure(e.message ?: "Registration failed")
        }
    }
    suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount): AuthResult? {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).await()
            AuthResult.Success
        } catch (e: Exception) {
            Log.e("AuthRepository", "Google Sign-In failed", e)
            AuthResult.Failure(e.message ?: "Google Sign-In failed")
        }
    }


    fun isUserLoggedIn(): Boolean = auth.currentUser != null
}
