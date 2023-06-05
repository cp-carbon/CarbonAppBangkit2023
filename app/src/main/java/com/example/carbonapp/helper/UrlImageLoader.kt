package com.example.carbonapp.helper

import android.widget.ImageView
import com.squareup.picasso.Picasso

class UrlImageLoader {

    companion object {
        fun load(url: String, imageView: ImageView) {
            Picasso.get()
                .load(url)
                .into(imageView)
        }
    }
}