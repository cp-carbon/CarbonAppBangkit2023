package com.example.carbonapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carbonapp.R
import com.example.carbonapp.helper.UrlImageLoader
import com.example.carbonapp.`object`.News

class HomeNewsAdapter : RecyclerView.Adapter<HomeNewsAdapter.ViewHolder>() {

    private val data = ArrayList<News>()

    fun updateData(posts: ArrayList<News>) {
        data.clear()
        data.addAll(posts)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bgView : ImageView
        val dateText : TextView
        val titleText : TextView
        val contentText : TextView

        init {
            bgView = itemView.findViewById(R.id.vh_htp_bg)
            dateText = itemView.findViewById(R.id.vh_htp_date)
            titleText = itemView.findViewById(R.id.vh_htp_title)
            contentText = itemView.findViewById(R.id.vh_htp_content)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vh_home_news, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(data[position]) {
            if (backgroundImageUrl != null) {
                UrlImageLoader.load(backgroundImageUrl, holder.bgView)
            }
            holder.dateText.text = publishedAt
            holder.titleText.text = title
            holder.contentText.text = description
        }
    }

    override fun getItemCount(): Int = data.size
}