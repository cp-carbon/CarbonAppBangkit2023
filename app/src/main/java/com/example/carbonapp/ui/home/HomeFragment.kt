package com.example.carbonapp.ui.home

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager.widget.ViewPager
import com.example.carbonapp.HomeOnNavigationItemSelected
import com.example.carbonapp.R
import com.example.carbonapp.component.EmissionTrackerView
import com.example.carbonapp.component.TravelActivitiesView
import com.example.carbonapp.library.activity_recognition.ActivityRecognitionService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var emissionTrackerView: EmissionTrackerView
    private lateinit var travelActivitiesView: TravelActivitiesView
    private lateinit var newsAdapter: HomeNewsAdapter
    private lateinit var productAdapter: HomeProductAdapter
    private lateinit var reqPermissionActivityResult: ActivityResultLauncher<Array<String>>

    private val activityHistory = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        newsAdapter = HomeNewsAdapter(requireContext())
        productAdapter = HomeProductAdapter(requireContext())
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { onStateUpdate(it) }
            }
        }

        reqPermissionActivityResult = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it[Manifest.permission.ACTIVITY_RECOGNITION] == true) {
                startServices()
            }
            reqPermissionActivityResult.unregister()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        emissionTrackerView = v.findViewById(R.id.h_emission_tracker)
        travelActivitiesView = v.findViewById(R.id.h_travel_activities)

        val viewPager = v.findViewById<ViewPager>(R.id.h_vp_news)
        viewPager.pageMargin = 48
        viewPager.adapter = newsAdapter

        val gridViewProduct = v.findViewById<GridView>(R.id.h_gv_product)
        gridViewProduct.adapter = productAdapter

        val fab = v.findViewById<ExtendedFloatingActionButton>(R.id.h_fab)
        fab.setOnClickListener {
            HomeAddActivityBottomSheetDialogFragment().show(parentFragmentManager, "bottom_sheet_add_activity")
            //openActivitiesDialog()
        }

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

        // TODO: uncomment this code to start testing activity recognition, and use openActivitiesDialog() function to show it's results
        //startServices()
    }

    private fun onStateUpdate(state: HomeUiState) {
        if (state.isLoading) {
            Toast.makeText(context, resources.getString(R.string.loading), Toast.LENGTH_SHORT).show()
            return
        }

        if (state.hasError) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
            return
        }

        emissionTrackerView.emissionValue = state.todayEmission

        travelActivitiesView.maxDistance = state.travelActivities!!.walk +
                state.travelActivities.bike +
                state.travelActivities.vehicle +
                state.travelActivities.publicTransport
        travelActivitiesView.walkDistance = state.travelActivities.walk
        travelActivitiesView.bikeDistance = state.travelActivities.bike
        travelActivitiesView.carDistance = state.travelActivities.vehicle
        travelActivitiesView.publicTransDistance = state.travelActivities.publicTransport

        newsAdapter.data = state.news
        productAdapter.data = state.products
    }

    private fun openActivitiesDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.apply {
            setTitle("Activity History")
            setMessage(activityHistory.joinToString("\n"))
            create().show()
        }
    }

    private fun isPermitted(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val p = arrayListOf<String>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                p.add(Manifest.permission.ACTIVITY_RECOGNITION)
            }

            reqPermissionActivityResult.launch(p.toArray(arrayOf<String>()))
            return false
        }

        return true
    }

    private fun startServices() {
        if (!isPermitted()) return

        val activityRecognitionFilter = "${requireContext().packageName}.Home.ActivityRecognition"
        val activityRecognitionIntent = Intent(requireContext(), ActivityRecognitionService::class.java)
        activityRecognitionIntent.putExtra("filter", activityRecognitionFilter)
        requireContext().startService(activityRecognitionIntent)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent != null) {
                        val activity = intent.getIntExtra("activity", -1)
                        val transition = intent.getIntExtra("transition", -1)

                        activityHistory.add("activity: $activity - transition: $transition")
                    }
                }
            },
            IntentFilter(activityRecognitionFilter)
        )
    }
}