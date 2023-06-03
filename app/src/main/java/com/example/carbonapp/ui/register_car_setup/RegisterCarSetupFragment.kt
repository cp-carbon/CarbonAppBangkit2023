package com.example.carbonapp.ui.register_car_setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carbonapp.R
import com.example.carbonapp.helper.Navigator

class RegisterCarSetupFragment : Fragment() {

    private lateinit var viewModel: RegisterCarSetupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterCarSetupViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_register_car_setup, container, false)

        val brandSpinner = v.findViewById<Spinner>(R.id.vehicle_brand)
        val classSpinner = v.findViewById<Spinner>(R.id.vehicle_class)
        val modelSpinner = v.findViewById<Spinner>(R.id.vehicle_model)
        val saveButton = v.findViewById<Button>(R.id.save)
        saveButton.setOnClickListener {
            viewModel.save(brandSpinner.selectedItem.toString(), classSpinner.selectedItem.toString(), modelSpinner.selectedItem.toString())
            navigateToPostRegister()
        }

        return v
    }

    private fun navigateToPostRegister() {
        Navigator.toRegisterDidYouKnowFragment(this)
    }
}