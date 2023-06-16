package com.example.carbonapp.ui.my_activity

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.carbonapp.HomeOnNavigationItemSelected
import com.example.carbonapp.R
import com.example.carbonapp.component.EmissionTrackerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MyActivityFragment : Fragment() {

    private lateinit var viewModel: MyActivityViewModel
    private lateinit var emissionTrackerView: EmissionTrackerView
    private lateinit var travelByWalkText: TextView
    private lateinit var travelByBikeText: TextView
    private lateinit var travelByCarText: TextView
    private lateinit var travelByPublicTransText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MyActivityViewModel::class.java]
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { onStateUpdate(it) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_my_activity, container, false)

        emissionTrackerView = v.findViewById(R.id.ma_emission_tracker)

        val emissionGeneratedText = v.findViewById<TextView>(R.id.ma_emission_generated)
        emissionGeneratedText.text = getString(R.string.emission_value, 1000)

        val minimumTravelDistanceText = v.findViewById<TextView>(R.id.ma_min_travel_distance)
        val s = String.format(resources.getString(R.string.min_travel_distance), 500)
        minimumTravelDistanceText.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)

        travelByWalkText = v.findViewById(R.id.ma_walk_distance)
        travelByBikeText = v.findViewById(R.id.ma_bike_distance)
        travelByCarText = v.findViewById(R.id.ma_car_distance)
        travelByPublicTransText = v.findViewById(R.id.ma_public_trans_distance)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNav = view.rootView.findViewById<BottomNavigationView>(R.id.bottom_nav_home)
        bottomNav.setOnItemSelectedListener(HomeOnNavigationItemSelected(this))
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }

    private fun onStateUpdate(state: MyActivityUiState) {
        if (state.isLoading) {
            Toast.makeText(context, resources.getString(R.string.loading), Toast.LENGTH_SHORT).show()
            return
        }

        if (state.hasError) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
            return
        }

        emissionTrackerView.emissionValue = state.averageEmission
        travelByWalkText.text = getString(R.string.travel_distance_km, state.travelStats!!.walk)
        travelByBikeText.text = getString(R.string.travel_distance_km, state.travelStats.bike)
        travelByCarText.text = getString(R.string.travel_distance_km, state.travelStats.vehicle)
        travelByPublicTransText.text = getString(R.string.travel_distance_km, state.travelStats.publicTransport)
    }
}