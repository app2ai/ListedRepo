package com.example.listedapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.listedapplication.R
import com.example.listedapplication.databinding.FragmentRecentLinksBinding
import com.example.listedapplication.databinding.FragmentTopLinksBinding

class TopLinksFragment : Fragment() {
    private lateinit var binding: FragmentTopLinksBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopLinksBinding.inflate(inflater, container, false)
        return binding.root
    }
}