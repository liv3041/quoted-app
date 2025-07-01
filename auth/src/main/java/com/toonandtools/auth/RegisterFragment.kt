package com.toonandtools.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

import com.toonandtools.auth.databinding.FragmentLoginBinding
import com.toonandtools.auth.databinding.FragmentRegisterBinding
import com.toonandtools.auth.repository.AuthRepository
import com.toonandtools.auth.util.AuthResult
import com.toonandtools.auth.viewmodel.AuthViewModel
import com.toonandtools.auth.viewmodel.AuthViewModelFactory
import com.toonandtools.auth.viewmodel.RegisterViewModel
import com.toonandtools.core_ui.CoreActivity


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var authViewModel: AuthViewModel
    private val GOOGLE_SIGN_IN = 1001


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val repository = AuthRepository(auth)
        val factory = AuthViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            val email = binding.emailLayout.editText?.text.toString().trim()
            val password = binding.passwordLayout.editText?.text.toString().trim()
            val confirmPassword = binding.confirmPasswordLayout.editText?.text.toString().trim()

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(email, password)
        }

        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is AuthResult.Success -> {
                    Toast.makeText(requireContext(), "Registered successfully", Toast.LENGTH_SHORT).show()
                    // Navigate to loginFragment
                }

                is AuthResult.Failure -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.regGoogle.setOnClickListener {
            val signInIntent = authViewModel.getGoogleSignInClient().signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        }


        binding.loginEvent.setOnClickListener {
         findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
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



}