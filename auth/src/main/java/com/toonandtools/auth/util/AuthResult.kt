package com.toonandtools.auth.util


sealed class AuthResult {
    object Success : AuthResult()
    data class Failure(val message: String) : AuthResult()
}
