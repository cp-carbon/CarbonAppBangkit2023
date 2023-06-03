package com.example.carbonapp.ui.account

import androidx.lifecycle.ViewModel
import com.example.carbonapp.data.LoginRepository

class AccountViewModel : ViewModel() {
    // TODO: Complete the ViewModel

    private val loginRepository = LoginRepository.instance

    fun logout() : Boolean {
        loginRepository.logout()
        return true
    }
}