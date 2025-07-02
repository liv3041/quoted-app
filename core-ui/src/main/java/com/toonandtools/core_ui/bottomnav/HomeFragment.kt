package com.toonandtools.core_ui.bottomnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.toonandtools.core_ui.R
import com.toonandtools.core_ui.adapter.QuoteAdapter
import com.toonandtools.core_ui.data.QuoteItem
import com.toonandtools.core_ui.databinding.FragmentHomeBinding
import com.toonandtools.core_ui.layout.CenterZoomLayoutVertical

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: QuoteAdapter
    private lateinit var quoteList:MutableList<QuoteItem>
    private lateinit var snapHelper: SnapHelper




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = CenterZoomLayoutVertical(requireContext())
        binding.quotesRecyclerView.layoutManager = layoutManager
        quoteList = mutableListOf()
        adapter = QuoteAdapter(quoteList)
        snapHelper = LinearSnapHelper()
        binding.quotesRecyclerView.adapter = adapter
        snapHelper.attachToRecyclerView(binding.quotesRecyclerView)
        quoteList.addAll(DummyQuotesData.quoteList)
        adapter.notifyDataSetChanged()


    }

    object DummyQuotesData {

        val quoteList = listOf(
            QuoteItem(
                quoteText = "Be yourself; everyone else is already taken.",
                quoteAuthor = "Oscar Wilde",
            ),
            QuoteItem(
                quoteText = "In the middle of every difficulty lies opportunity.",
                quoteAuthor = "Albert Einstein",
            ),
            QuoteItem(
                quoteText = "Success is not final, failure is not fatal: It is the courage to continue that counts.",
                quoteAuthor = "Winston Churchill",
            ),
            QuoteItem(
                quoteText = "The only way to do great work is to love what you do.",
                quoteAuthor = "Steve Jobs",
            ),
            QuoteItem(
                quoteText = "The future belongs to those who believe in the beauty of their dreams.",
                quoteAuthor = "Eleanor Roosevelt",
            ),
            QuoteItem(
                quoteText = "Happiness can be found even in the darkest of times if one only remembers to turn on the light.",
                quoteAuthor = "Albus Dumbledore",
            ),
            QuoteItem(
                quoteText = "Do what you can, with what you have, where you are.",
                quoteAuthor = "Theodore Roosevelt",
            )
        )
    }


}