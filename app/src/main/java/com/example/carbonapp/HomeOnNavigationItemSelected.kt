package com.example.carbonapp

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.carbonapp.helper.Navigator
import com.example.carbonapp.ui.account.AccountFragment
import com.example.carbonapp.ui.home.HomeFragment
import com.example.carbonapp.ui.my_activity.MyActivityFragment
import com.google.android.material.navigation.NavigationBarView

class HomeOnNavigationItemSelected(private val fragment: Fragment) : NavigationBarView.OnItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val destination = item.title
        val home = fragment.resources.getString(R.string.menu_home)
        val myActivity = fragment.resources.getString(R.string.menu_my_activity)
        val account = fragment.resources.getString(R.string.menu_account)

        when (fragment) {
            is HomeFragment -> when (destination) {
                myActivity -> Navigator.toMyActivityFragment(fragment)
                account -> Navigator.toAccountFragment(fragment)
            }
            is MyActivityFragment -> when (destination) {
                home -> Navigator.toHomeFragment(fragment)
                account -> Navigator.toAccountFragment(fragment)
            }
            is AccountFragment -> when (destination) {
                home -> Navigator.toHomeFragment(fragment)
                myActivity -> Navigator.toMyActivityFragment(fragment)
            }
        }

        return true
    }
}