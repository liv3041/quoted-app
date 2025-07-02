package com.toonandtools.core_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toonandtools.core_ui.data.QuoteItem
import com.toonandtools.core_ui.databinding.ItemQuotesBinding

class QuoteAdapter(private val quotes: List<QuoteItem>) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {
    inner class QuoteViewHolder(val binding: ItemQuotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = ItemQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return quotes.size
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = quotes[position]
        holder.binding.quoteText.text = item.quoteText
        holder.binding.quoteAuthor.text = item.quoteAuthor
    }

}