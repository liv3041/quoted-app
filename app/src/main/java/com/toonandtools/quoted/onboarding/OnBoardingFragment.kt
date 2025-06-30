package com.toonandtools.quoted.onboarding

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearSmoothScroller

import com.toonandtools.quoted.R
import com.toonandtools.quoted.databinding.FragmentOnBoardingBinding
import com.toonandtools.quoted.onboarding.adapter.OnboardingAdapter
import com.toonandtools.quoted.onboarding.model.QuoteSlide

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var adapter: OnboardingAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val handler = Handler(Looper.getMainLooper())
    private var currentIndex = 0

    private val slides = listOf(
        QuoteSlide(R.drawable.image1, "Believe in yourself!"),
        QuoteSlide(R.drawable.image2, "Your mindset defines your future"),
        QuoteSlide(R.drawable.image3, "Every day is a new beginning")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = OnboardingAdapter(slides)

        binding.onboardingRecyclerView.layoutManager = layoutManager
        binding.onboardingRecyclerView.adapter = adapter

        setupDots()
        highlightDot(0)

        binding.backButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                scrollToSlide(currentIndex)
            }
        }

        binding.nextButton.setOnClickListener {
            if (currentIndex < slides.size - 1) {
                currentIndex++
                scrollToSlide(currentIndex)
            }else{
                navigateToLoginFragment()
            }
        }
        binding.skipButton.setOnClickListener {
            navigateToLoginFragment()
        }

        binding.onboardingRecyclerView.registerOnScrollListener()
        startAutoScroll()
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
    }

    private fun scrollToSlide(index: Int) {
        binding.onboardingRecyclerView.smoothScrollToPosition(index)
        highlightDot(index)
    }

    private fun setupDots() {
        binding.dotContainer.removeAllViews()
        repeat(slides.size) {
            val dot = ImageView(requireContext()).apply {
                setImageResource(R.drawable.dot_inactive)
                val params = LinearLayout.LayoutParams(20, 20)
                params.marginEnd = 12
                layoutParams = params
            }
            binding.dotContainer.addView(dot)
        }
    }

    private fun highlightDot(index: Int) {
        currentIndex = index
        for (i in 0 until binding.dotContainer.childCount) {
            val dot = binding.dotContainer.getChildAt(i) as ImageView
            dot.setImageResource(
                if (i == index) R.drawable.dot_active else R.drawable.dot_inactive
            )
        }
    }

    private fun startAutoScroll() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (currentIndex < slides.size - 1) {
                    currentIndex++
                    scrollToSlide(currentIndex)
                    handler.postDelayed(this, 3000)
                } else {
                    // Stop auto-scroll on last slide
                    handler.removeCallbacks(this)
                }
            }
        }, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    private fun RecyclerView.registerOnScrollListener() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val visible = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (visible != RecyclerView.NO_POSITION) {
                        highlightDot(visible)
                    }
                }
            }
        })
    }
}

