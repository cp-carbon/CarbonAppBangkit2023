package com.example.carbonapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.carbonapp.R
import com.example.carbonapp.helper.UrlImageLoader
import com.example.carbonapp.`object`.Product

class HomeProductAdapter(private val context: Context) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var nameView: TextView

    var data = arrayListOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Product = data[position]

    override fun getItemId(position: Int): Long = data[position].title.hashCode().toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var cv = convertView

        if (layoutInflater == null) {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (cv == null) {
            cv = layoutInflater!!.inflate(R.layout.vh_home_product, parent, false)
        }

        imageView = cv!!.findViewById(R.id.vhhp_image)
        nameView = cv.findViewById(R.id.vhhp_name)

        with(getItem(position)) {
            UrlImageLoader.load(backgroundImageUrl, imageView)
            nameView.text = title
        }

        return cv
    }
}