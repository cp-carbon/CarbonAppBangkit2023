package com.example.carbonapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        if (viewModel.isLoggedIn()) {
            navigateToHomeActivity()
            return
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { onStateUpdate(it) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        val emailInput = v.findViewById<EditText>(R.id.l_email)
        val passwordInput = v.findViewById<EditText>(R.id.l_password)
        val signInButton = v.findViewById<Button>(R.id.l_login)
        signInButton.setOnClickListener { viewModel.login(emailInput.text.toString(), passwordInput.text.toString()) }
        val signInWithGoogleButton = v.findViewById<Button>(R.id.l_login_google)
        signInWithGoogleButton.setOnClickListener { viewModel.loginWithGoogle() }

        val signupText = v.findViewById<TextView>(R.id.l_signup)
        signupText.setOnClickListener { navigateToRegister() }

        return v
    }

    private fun onStateUpdate(state: LoginUiState) {
        if (state.isLoading) {
            Toast.makeText(context, resources.getString(R.string.loading), Toast.LENGTH_SHORT).show()
            return
        }

        if (state.isLoginSuccess) {
            navigateToHomeActivity()
        } else if (state.errorMessage != null) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToRegister() {
        Navigator.toRegisterFragment(this)
    }

    private fun navigateToHomeActivity() {
        Navigator.toHomeActivity(requireActivity())
    }
}