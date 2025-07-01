package com.toonandtools.auth.viewmodel



import androidx.lifecycle.*
import com.toonandtools.auth.repository.AuthRepository
import com.toonandtools.auth.util.AuthResult
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<AuthResult>()
    val registerResult: LiveData<AuthResult> = _registerResult

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerResult.value = repository.register(email, password)
        }
    }
}
