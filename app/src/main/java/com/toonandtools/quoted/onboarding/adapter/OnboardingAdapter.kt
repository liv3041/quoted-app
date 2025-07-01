package com.toonandtools.quoted.onboarding.adapter

import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.toonandtools.quoted.databinding.ItemOnboardingSlideBinding
import com.toonandtools.quoted.onboarding.model.QuoteSlide

class OnboardingAdapter(private val slides: List<QuoteSlide>) :
    RecyclerView.Adapter<OnboardingAdapter.SlideViewHolder>() {

    inner class SlideViewHolder(val binding: ItemOnboardingSlideBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val binding = ItemOnboardingSlideBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SlideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val item = slides[position]
        holder.binding.imageView.setImageResource(item.imageRes)
        holder.binding.quoteText.text = item.quote
    }

    override fun getItemCount() = slides.size
}

