package com.example.listedapplication.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.listedapplication.R
import com.example.listedapplication.databinding.LinkCardBinding
import com.example.listedapplication.model.LinkDetails
import com.example.listedapplication.utils.DateUtils
import com.squareup.picasso.Picasso

class LinksAdapter(private val links: List<LinkDetails>) : Adapter<LinksAdapter.LinksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksViewHolder {
        val v = LinkCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LinksViewHolder(v)
    }

    override fun getItemCount(): Int {
        return links.size
    }

    override fun onBindViewHolder(holder: LinksViewHolder, position: Int) {
        holder.bindItem(links[position])
    }

    inner class LinksViewHolder(private val bind: LinkCardBinding): ViewHolder(bind.root) {
        fun bindItem(model: LinkDetails) {
            bind.linkName.text = model.title
            bind.linkDate.text = getFormatDate(model.created_at)
            bind.clickCountTxt.text = model.total_clicks.toString()
            bind.link.text = model.web_link
            Log.d("WEB", "Secure domain: ${model.web_link}")
            Picasso.get()
                .load(model.original_image)
                .fit()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading_fail)
                .into(bind.imgLogo)
        }

        private fun getFormatDate(d: String) : String{
            return DateUtils.dateFormatConversion(d)
        }
    }
}