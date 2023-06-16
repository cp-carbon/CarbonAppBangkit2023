package com.example.carbonapp.ui.register_car_setup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.carbonapp.R
import com.example.carbonapp.helper.Navigator
import kotlinx.coroutines.launch

class RegisterCarSetupFragment : Fragment() {

    private lateinit var viewModel: RegisterCarSetupViewModel
    private lateinit var brandSpinnerAdapter: LocalSpinnerAdapter
    private lateinit var classSpinnerAdapter: LocalSpinnerAdapter
    private lateinit var modelSpinnerAdapter: LocalSpinnerAdapter

    private lateinit var brandSpinner: Spinner
    private lateinit var classSpinner: Spinner
    private lateinit var modelSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterCarSetupViewModel::class.java]
        brandSpinnerAdapter = LocalSpinnerAdapter(requireContext())
        classSpinnerAdapter = LocalSpinnerAdapter(requireContext())
        modelSpinnerAdapter = LocalSpinnerAdapter(requireContext())
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
        val v = inflater.inflate(R.layout.fragment_register_car_setup, container, false)

        brandSpinner = v.findViewById(R.id.vehicle_brand)
        classSpinner = v.findViewById(R.id.vehicle_class)
        modelSpinner = v.findViewById(R.id.vehicle_model)
        val saveButton = v.findViewById<Button>(R.id.save)
        saveButton.setOnClickListener {
            viewModel.save(brandSpinner.selectedItem.toString(), classSpinner.selectedItem.toString(), modelSpinner.selectedItem.toString())
            navigateToPostRegister()
        }

        return v
    }

    private fun onStateUpdate(state: RegisterCarSetupUiState) {
        if (state.isLoading) {
            Toast.makeText(context, resources.getString(R.string.loading), Toast.LENGTH_SHORT).show()
            return
        }

        if (state.hasError) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
            return
        }

        if (state.brands.isNotEmpty()) {
            brandSpinner.adapter = brandSpinnerAdapter
            brandSpinnerAdapter.data = state.brands
        }
        if (state.classes.isNotEmpty()) {
            classSpinner.adapter = classSpinnerAdapter
            classSpinnerAdapter.data = state.classes
        }
        if (state.models.isNotEmpty()) {
            modelSpinner.adapter = modelSpinnerAdapter
            modelSpinnerAdapter.data = state.models
        }
    }

    private fun navigateToPostRegister() {
        Navigator.toRegisterDidYouKnowFragment(this)
    }

    inner class LocalSpinnerAdapter(private val context: Context) : BaseAdapter() {

        private var layoutInflater: LayoutInflater? = null
        private lateinit var textView: TextView

        var data = arrayListOf<String>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): String {
            return data[position]
        }

        override fun getItemId(position: Int): Long {
            return data[position].hashCode().toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var cv = convertView

            if (layoutInflater == null) {
                layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            }

            if (cv == null) {
                cv = layoutInflater!!.inflate(android.R.layout.simple_spinner_item, parent, false)
            }

            textView = cv!!.findViewById(android.R.id.text1)
            textView.text = getItem(position)

            return cv
        }
    }
}