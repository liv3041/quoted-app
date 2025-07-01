package com.toonandtools.auth.viewmodel



import androidx.lifecycle.*
import com.toonandtools.auth.repository.AuthRepository
import com.toonandtools.auth.util.AuthResult
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<AuthResult>()
    val loginResult: LiveData<AuthResult> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = repository.login(email, password)
        }
    }
}
