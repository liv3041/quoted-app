package com.toonandtools.auth.phoneauth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.toonandtools.auth.R
import com.toonandtools.auth.databinding.FragmentPhoneBinding
import com.toonandtools.auth.viewmodel.AuthViewModel


class PhoneFragment : Fragment() {

    private lateinit var binding: FragmentPhoneBinding
    private lateinit var authViewModel: AuthViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhoneBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.countryCodePicker.registerCarrierNumberEditText(binding.phoneEditText)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.sendOtpButton.setOnClickListener {
            val phone = binding.countryCodePicker.fullNumberWithPlus

//            val phone = binding.countryCodePicker.fullNumberWithPlus + binding.phoneEditText.text.toString()
            authViewModel.startPhoneAuth(requireActivity(), phone) { verificationId ->
                val bundle = Bundle().apply {
                    putString("verificationId", verificationId)
                    putString("phone", phone)
                }
                findNavController().navigate(R.id.action_phoneFragment_to_otpFragment, bundle)

            }
        }
    }

}