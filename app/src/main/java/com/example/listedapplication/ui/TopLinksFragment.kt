package com.example.listedapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.listedapplication.MainActivity
import com.example.listedapplication.databinding.FragmentTopLinksBinding
import com.example.listedapplication.model.LinkDetails
import com.example.listedapplication.utils.FetchLinksFromRemoteListener

class TopLinksFragment : Fragment() {
    private lateinit var binding: FragmentTopLinksBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentTopLinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).passInterface(object : FetchLinksFromRemoteListener {
            override fun onLinksFetched(list: List<LinkDetails>) {
                setupRecyclerView(list)
            }
        })
    }

    private fun setupRecyclerView(data: List<LinkDetails>) {
        binding.recyclerTopLinks.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            adapter = LinksAdapter(data)
        }
    }
}