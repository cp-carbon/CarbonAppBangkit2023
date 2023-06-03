package com.example.carbonapp.ui.register_pick_preference

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carbonapp.R
import com.example.carbonapp.helper.Navigator
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class RegisterPickPreferenceFragment : Fragment() {

    private lateinit var viewModel: RegisterPickPreferenceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterPickPreferenceViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_register_pick_preference, container, false)

        val preferenceGroup = v.findViewById<ChipGroup>(R.id.transportation_preference_group)

        val nextButton = v.findViewById<Button>(R.id.next)
        nextButton.setOnClickListener {
            viewModel.clearPreferences()
            preferenceGroup.checkedChipIds.forEach { id ->
                val preference = preferenceGroup.findViewById<Chip>(id).text.toString()
                viewModel.addPreference(preference)
            }
            viewModel.savePreferences()

            // if Car is selected, go to car setup. otherwise go to did you know fragment
            if (viewModel.isCarSelected(resources.getString(R.string.car))) navigateToCarSetup()
            else navigateToRegisterDidYouKnow()
        }

        return v
    }

    private fun navigateToCarSetup() {
        Navigator.toRegisterCarSetupFragment(this)
    }

    private fun navigateToRegisterDidYouKnow() {
        Navigator.toRegisterDidYouKnowFragment(this)
    }
}