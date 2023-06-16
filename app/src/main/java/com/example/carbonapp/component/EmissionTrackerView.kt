package com.example.carbonapp.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.example.carbonapp.R
import com.google.android.material.progressindicator.LinearProgressIndicator

class EmissionTrackerView @JvmOverloads
    constructor(ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : CardView(ctx, attrs, defStyleAttr) {

    private val titleText: TextView
    private val contributionSumText: TextView
    private val progressIndicator: LinearProgressIndicator
    private val percentageText: TextView

    var duration: Duration = Duration.Average
        set(value) {
            val duration: String =
                if (value == Duration.Today) "Today"
                else "Average"
            titleText.text = resources.getString(R.string.component_emission_tracker_title, duration)
            field = value
        }

    var emissionValue: Int = 0
        set(value) {
            contributionSumText.text = resources.getString(R.string.emission_value, value)
            progressIndicator.setProgressCompat(value, true)
            field = value
            setPercentageText()
        }

    var emissionMaxValue: Int = 100
        set(value) {
            progressIndicator.max = value
            field = value
            setPercentageText()
        }

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.component_emission_tracker, this)

        titleText = findViewById(R.id.cet_title)
        contributionSumText = findViewById(R.id.cet_value)
        progressIndicator = findViewById(R.id.cet_progress)
        percentageText = findViewById(R.id.cet_percentage)

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.EmissionTracker)
        duration = Duration.values()[a.getString(R.styleable.EmissionTracker_timeLength)?.toIntOrNull() ?: 0]
        emissionMaxValue = a.getInt(R.styleable.EmissionTracker_emissionMaxValue, 20400)
        emissionValue = a.getInt(R.styleable.EmissionTracker_emissionValue, 0)
        a.recycle()

        background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_card, null)
    }

    private fun setPercentageText() {
        if (emissionValue == 0 || emissionMaxValue == 0) {
            percentageText.text = resources.getString(R.string.component_emission_tracker_percentage, 100)
            return
        }

        val p = 100 - (emissionValue * 100 / emissionMaxValue)
        percentageText.text = resources.getString(R.string.component_emission_tracker_percentage, p)
    }

    enum class Duration {
        Today, Average
    }
}