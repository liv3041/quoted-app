package com.toonandtools.auth.phoneauth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.toonandtools.auth.R
import com.toonandtools.auth.databinding.FragmentOtpBinding
import com.toonandtools.auth.viewmodel.AuthViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var verificationId: String
    private lateinit var phone: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        verificationId = arguments?.getString("verificationId") ?: ""
        phone = arguments?.getString("phone") ?: ""

        binding.verifyOtpButton.setOnClickListener {
            val otp = binding.otp1.text.toString().trim() +
                    binding.otp2.text.toString().trim() +
                    binding.otp3.text.toString().trim() +
                    binding.otp4.text.toString().trim() +
                    binding.otp5.text.toString().trim() +
                    binding.otp6.text.toString().trim()

            viewModel.verifyOtp(verificationId, otp) { success ->
                if (success) {
                    Toast.makeText(requireContext(), "Verified!", Toast.LENGTH_SHORT).show()
                    // Navigate to Dashboard
                } else {
                    Toast.makeText(requireContext(), "Invalid OTP", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}