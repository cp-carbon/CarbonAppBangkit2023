package com.example.carbonapp.ui.account

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.carbonapp.HomeOnNavigationItemSelected
import com.example.carbonapp.R
import com.example.carbonapp.helper.Navigator
import com.google.android.material.bottomnavigation.BottomNavigationView

class AccountFragment : Fragment() {

    private lateinit var viewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        // TODO: use the viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_account, container, false)

        val logoutButton = v.findViewById<Button>(R.id.a_logout)
        logoutButton.setOnClickListener {
            if (viewModel.logout()) {
                navigateToLoginActivity()
            }
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNav = view.rootView.findViewById<BottomNavigationView>(R.id.bottom_nav_home)
        bottomNav.setOnItemSelectedListener(HomeOnNavigationItemSelected(this))
    }

    private fun navigateToLoginActivity() {
        Navigator.toLoginActivity(requireActivity())
    }
}