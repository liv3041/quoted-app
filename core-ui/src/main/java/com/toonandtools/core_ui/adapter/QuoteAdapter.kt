package com.toonandtools.core_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.toonandtools.core_ui.R
import com.toonandtools.core_ui.data.QuoteItem
import com.toonandtools.core_ui.databinding.ItemQuotesBinding
import com.toonandtools.core_ui.viewmodel.UserViewmodel

class QuoteAdapter(private val quotes: List<QuoteItem>,private val userViewmodel: UserViewmodel) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {
    inner class QuoteViewHolder(val binding: ItemQuotesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: QuoteItem) {
            val firebaseUser = FirebaseAuth.getInstance().currentUser

            binding.quoteItem = item
            binding.favIcon.setOnClickListener(null)

            if(firebaseUser!=null) {
                val firebaseUserId = firebaseUser.uid
                userViewmodel.isFavorite(firebaseUserId, item.quoteID).observeForever { isfavorite ->
                    binding.favIcon.setImageResource(if(isfavorite)R.drawable.favfilled else R.drawable.fav)
//                    if(isfavorite){
//                        binding.favIcon.setOnClickListener {
//                            userViewmodel.removeFavorite(firebaseUserId,item.quoteId)
//
//                        }
//                    }else{
//                        binding.favIcon.setOnClickListener {
//                            userViewmodel.addFavoriteItem(
//                                firebaseUserId,
//                                item.quoteId
//                            )
//
//                        }
//
//
//                    }

                    binding.favIcon.setOnClickListener {
                        if (isfavorite) {
                            userViewmodel.removeFavorite(firebaseUserId, item.quoteID)
                        } else {
                            userViewmodel.addFavoriteItem(firebaseUserId, item.quoteID,isFavorite=true)
                        }
                    }
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ItemQuotesBinding>(inflater,
            R.layout.item_quotes, parent, false)
//        val binding = ItemQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return quotes.size
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = quotes[position]
        holder.bind(item)
        holder.binding.quoteText.text = item.quoteText
        holder.binding.quoteAuthor.text = item.quoteAuthor
    }

}