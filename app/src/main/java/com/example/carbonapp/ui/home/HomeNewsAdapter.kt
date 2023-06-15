package com.example.carbonapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.example.carbonapp.R
import com.example.carbonapp.helper.UrlImageLoader
import com.example.carbonapp.`object`.News
import java.util.Objects

class HomeNewsAdapter(private val ctx : Context) : PagerAdapter() {

    private val data = ArrayList<News>()

    fun updateData(posts: ArrayList<News>) {
        data.clear()
        data.addAll(posts)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as CardView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // on below line we are initializing
        // our layout inflater.
        val mLayoutInflater =
            ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // on below line we are inflating our custom
        // layout file which we have created.
        val itemView: View = mLayoutInflater.inflate(R.layout.vh_home_news, container, false)

        val bgView : ImageView = itemView.findViewById(R.id.vh_htp_bg)
        val dateText : TextView = itemView.findViewById(R.id.vh_htp_date)
        val titleText : TextView = itemView.findViewById(R.id.vh_htp_title)
        val contentText : TextView = itemView.findViewById(R.id.vh_htp_content)

        with(data[position]) {
            if (backgroundImageUrl != null) {
                UrlImageLoader.load(backgroundImageUrl, bgView)
            }
            dateText.text = "$author - $publishedAt"
            titleText.text = title
            contentText.text = description
        }

        Objects.requireNonNull(container).addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // on below line we are removing view
        container.removeView(`object` as CardView)
    }
}