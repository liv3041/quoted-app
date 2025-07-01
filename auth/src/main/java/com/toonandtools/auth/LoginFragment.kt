package com.toonandtools.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

import com.toonandtools.auth.databinding.FragmentLoginBinding
import com.toonandtools.auth.repository.AuthRepository
import com.toonandtools.auth.util.AuthResult
import com.toonandtools.auth.viewmodel.AuthViewModel
import com.toonandtools.auth.viewmodel.AuthViewModelFactory
import com.toonandtools.auth.viewmodel.LoginViewModel
import com.toonandtools.core_ui.CoreActivity


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var authViewModel: AuthViewModel
    private val GOOGLE_SIGN_IN = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        val repository = AuthRepository(auth)
        val factory = AuthViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]



        binding.btnLogin.setOnClickListener {
            val email = binding.emailLayout.editText?.text.toString().trim()
            val password = binding.passwordLayout.editText?.text.toString().trim()

            viewModel.login(email, password)
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is AuthResult.Success -> {
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    // Navigate to Main/Dashboard
                    startActivity(Intent(requireContext(), CoreActivity::class.java))
                }

                is AuthResult.Failure -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.createAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.googleButton.setOnClickListener {
            val signInIntent = authViewModel.getGoogleSignInClient().signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    authViewModel.signInWithGoogle(it) { success ->
                        if (success) {
                            Toast.makeText(requireContext(), "Signed in as ${it.displayName}", Toast.LENGTH_SHORT).show()
                            // Navigate to next screen or update UI
                            startActivity(Intent(requireContext(), CoreActivity::class.java))
                        } else {
                            Toast.makeText(requireContext(), "Sign-in failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}