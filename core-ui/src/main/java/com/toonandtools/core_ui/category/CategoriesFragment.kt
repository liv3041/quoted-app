package com.toonandtools.core_ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toonandtools.core_ui.R
import com.toonandtools.core_ui.databinding.FragmentCategoriesBinding


class CategoriesFragment : Fragment() {

    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString(ARG_CATEGORY) ?: "Unknown"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewCategory.text = "Quotes for: $category"
    }
    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: String) = CategoriesFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CATEGORY, category)
            }
        }
    }


}