package com.toonandtools.auth.repository



import com.google.firebase.auth.FirebaseAuth
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

    fun isUserLoggedIn(): Boolean = auth.currentUser != null
}
