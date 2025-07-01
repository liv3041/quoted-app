package com.toonandtools.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

import com.toonandtools.auth.databinding.FragmentLoginBinding
import com.toonandtools.auth.repository.AuthRepository
import com.toonandtools.auth.util.AuthResult
import com.toonandtools.auth.viewmodel.AuthViewModelFactory
import com.toonandtools.auth.viewmodel.LoginViewModel


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

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
                }

                is AuthResult.Failure -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.createAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}