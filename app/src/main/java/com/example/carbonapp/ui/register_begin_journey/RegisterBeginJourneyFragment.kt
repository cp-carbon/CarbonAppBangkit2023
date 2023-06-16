package com.example.carbonapp.ui.register_begin_journey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.carbonapp.R
import com.example.carbonapp.helper.Navigator
import kotlinx.coroutines.launch

class RegisterBeginJourneyFragment : Fragment() {

    private lateinit var viewModel: RegisterBeginJourneyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterBeginJourneyViewModel::class.java]
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
        val v = inflater.inflate(R.layout.fragment_register_begin_journey, container, false)

        val beginJourneyButton = v.findViewById<Button>(R.id.begin_journey)
        beginJourneyButton.setOnClickListener { viewModel.register() }

        return v
    }

    private fun onStateUpdate(state: RegisterBeginJourneyUiState) {
        if (state.isLoading) {
            Toast.makeText(context, resources.getString(R.string.loading), Toast.LENGTH_SHORT).show()
            return
        }

        if (state.isSuccessRegister) {
            navigateToMainActivity()
        } else if (state.errorMessage != null) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMainActivity() {
        Navigator.toHomeActivity(requireActivity())
    }
}