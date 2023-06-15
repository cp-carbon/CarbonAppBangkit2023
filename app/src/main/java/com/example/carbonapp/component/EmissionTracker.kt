package com.example.carbonapp.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.carbonapp.R
import com.google.android.material.progressindicator.LinearProgressIndicator

class EmissionTracker @JvmOverloads
    constructor(ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : CardView(ctx, attrs, defStyleAttr) {

    init {
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.EmissionTracker)
        val backgroundColor = a.getColor(R.styleable.EmissionTracker_backgroundColor, resources.getColor(android.R.color.white, null))
        val timeLength = a.getString(R.styleable.EmissionTracker_timeLength)
        val emissionValue = a.getInt(R.styleable.EmissionTracker_emissionValue, 0)
        val progressPercentage = a.getFloat(R.styleable.EmissionTracker_progressPercentage, 0.0f)
        val contentPadding = a.getDimensionPixelSize(R.styleable.EmissionTracker_contentPadding, 40)
        a.recycle()

        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.component_emission_tracker, this)

        val titleText = findViewById<TextView>(R.id.cet_title)
        val contributionSumText = findViewById<TextView>(R.id.cet_value)
        val progress = findViewById<LinearProgressIndicator>(R.id.cet_progress)
        val percentageText = findViewById<TextView>(R.id.cet_percentage)

        setCardBackgroundColor(backgroundColor)
        val duration: String =
            if (timeLength == "0") "Today"
            else "Average"
        titleText.text = resources.getString(R.string.component_emission_tracker_title, duration)
        contributionSumText.text = resources.getString(R.string.emission_value, emissionValue)
        progress.progress = (progressPercentage * 100).toInt()
        percentageText.text = resources.getString(R.string.component_emission_tracker_percentage, (progressPercentage * 100).toInt())
        setContentPadding(
            contentPadding, contentPadding, contentPadding, contentPadding
        )

        background = resources.getDrawable(R.drawable.rounded_card, null)
    }
}