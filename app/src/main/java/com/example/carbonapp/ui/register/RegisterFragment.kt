package com.example.carbonapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carbonapp.R
import com.example.carbonapp.helper.Navigator

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_register, container, false)

        val backButton = v.findViewById<Button>(R.id.r_back)
        backButton.setOnClickListener { navigateUp() }

        val fullNameInput = v.findViewById<EditText>(R.id.r_fullname)
        val emailInput = v.findViewById<EditText>(R.id.r_email)
        val passwordInput = v.findViewById<EditText>(R.id.r_password)
        val confirmPasswordInput = v.findViewById<EditText>(R.id.r_confirmpassword)
        val registerButton = v.findViewById<Button>(R.id.r_register)
        registerButton.setOnClickListener { onRegister(fullNameInput.text.toString(), emailInput.text.toString(), passwordInput.text.toString(), confirmPasswordInput.text.toString()) }

        return v
    }

    private fun onRegister(fullName: String, email: String, password: String, confirmPassword: String) {
        val errorValidateMessage = viewModel.validate(fullName, email, password, confirmPassword)

        if (errorValidateMessage != null) {
            Toast.makeText(context, errorValidateMessage, Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.save(fullName, email, password, confirmPassword)
        navigateToRegisterPickPreference()
    }

    private fun navigateUp() {
        Navigator.upFragment(this)
    }

    private fun navigateToRegisterPickPreference() {
        Navigator.toRegisterPickPreferenceFragment(this)
    }
}