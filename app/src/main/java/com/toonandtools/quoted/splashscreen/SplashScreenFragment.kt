package com.toonandtools.quoted.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.toonandtools.quoted.R
import com.toonandtools.quoted.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {
   private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        val scaleUp = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up)

        binding.logoImage.startAnimation(scaleUp)
        binding.textView.startAnimation(fadeIn) // Make sure the ID in your XML is correct

        // Optional: move to next screen after a delay
        Handler(Looper.getMainLooper()).postDelayed({
            // Example: navigate to next fragment
            // findNavController().navigate(R.id.action_splash_to_home)
            findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
        }, 2000)
    }


}