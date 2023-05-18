package com.example.listedapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.listedapplication.R
import com.example.listedapplication.databinding.FragmentRecentLinksBinding

class RecentLinksFragment : Fragment() {

    private lateinit var binding: FragmentRecentLinksBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecentLinksBinding.inflate(inflater, container, false)
        return binding.root
    }
}