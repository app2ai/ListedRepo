package com.example.listedapplication.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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
            bind.copy.setOnClickListener {
                val clipboard: ClipboardManager = bind.root.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip: ClipData = ClipData.newPlainText("URL", links[adapterPosition].web_link)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(bind.root.context, "URL copied: ${clipboard.primaryClip?.getItemAt(0)?.text}", Toast.LENGTH_SHORT).show()
            }
        }

        private fun getFormatDate(d: String) : String{
            return DateUtils.dateFormatConversion(d)
        }
    }
}