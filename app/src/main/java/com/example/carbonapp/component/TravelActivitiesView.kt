package com.example.carbonapp.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.example.carbonapp.R
import com.google.android.material.progressindicator.LinearProgressIndicator

class TravelActivitiesView @JvmOverloads
    constructor(ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : CardView(ctx, attrs, defStyleAttr) {

    private val walkText: TextView
    private val walkProgressIndicator: LinearProgressIndicator
    private val bikeText: TextView
    private val bikeProgressIndicator: LinearProgressIndicator
    private val carText: TextView
    private val carProgressIndicator: LinearProgressIndicator
    private val publicTransText: TextView
    private val publicTransProgressIndicator: LinearProgressIndicator

    var walkDistance: Int = 0
        set(value) {
            walkText.text = resources.getString(R.string.travel_distance_km, value)
            walkProgressIndicator.setProgressCompat(value, true)
            field = value
        }

    var bikeDistance: Int = 0
        set(value) {
            bikeText.text = resources.getString(R.string.travel_distance_km, value)
            bikeProgressIndicator.setProgressCompat(value, true)
            field = value
        }

    var carDistance: Int = 0
        set(value) {
            carText.text = resources.getString(R.string.travel_distance_km, value)
            carProgressIndicator.setProgressCompat(value, true)
            field = value
        }

    var publicTransDistance: Int = 0
        set(value) {
            publicTransText.text = resources.getString(R.string.travel_distance_km, value)
            publicTransProgressIndicator.setProgressCompat(value, true)
            field = value
        }

    var maxDistance: Int = 0
        set(value) {
            walkProgressIndicator.max = value
            bikeProgressIndicator.max = value
            carProgressIndicator.max = value
            publicTransProgressIndicator.max = value
            field = value
        }

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.component_travel_activities, this)

        walkText = findViewById(R.id.cta_walk_text)
        walkProgressIndicator = findViewById(R.id.cta_walk_progress)
        bikeText = findViewById(R.id.cta_bike_text)
        bikeProgressIndicator = findViewById(R.id.cta_bike_progress)
        carText = findViewById(R.id.cta_car_text)
        carProgressIndicator = findViewById(R.id.cta_car_progress)
        publicTransText = findViewById(R.id.cta_public_trans_text)
        publicTransProgressIndicator = findViewById(R.id.cta_public_trans_progress)

        val a = ctx.obtainStyledAttributes(attrs, R.styleable.TravelActivities)
        val aWalkDistance = a.getInt(R.styleable.TravelActivities_walkDistance, 0)
        val aBikeDistance = a.getInt(R.styleable.TravelActivities_bikeDistance, 0)
        val aCarDistance = a.getInt(R.styleable.TravelActivities_carDistance, 0)
        val aPublicTransDistance = a.getInt(R.styleable.TravelActivities_publicTransDistance, 0)
        a.recycle()

        maxDistance = aWalkDistance + aBikeDistance + aCarDistance + aPublicTransDistance
        walkDistance = aWalkDistance
        bikeDistance = aBikeDistance
        carDistance = aCarDistance
        publicTransDistance = aPublicTransDistance

        background = ResourcesCompat.getDrawable(resources, R.drawable.rounded_card, null)
    }
}