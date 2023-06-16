package com.example.carbonapp.helper

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.carbonapp.HomeActivity
import com.example.carbonapp.LoginActivity
import com.example.carbonapp.R
import com.example.carbonapp.ScanFoodActivity
import com.example.carbonapp.ui.account.AccountFragment
import com.example.carbonapp.ui.home.HomeFragment
import com.example.carbonapp.ui.login.LoginFragment
import com.example.carbonapp.ui.my_activity.MyActivityFragment
import com.example.carbonapp.ui.register.RegisterFragment
import com.example.carbonapp.ui.register_car_setup.RegisterCarSetupFragment
import com.example.carbonapp.ui.register_did_you_know.RegisterDidYouKnowFragment
import com.example.carbonapp.ui.register_pick_preference.RegisterPickPreferenceFragment

class Navigator {

    companion object {

        fun toHomeActivity(activity: Activity) {
            val intent = Intent(activity, HomeActivity().javaClass)
            with(activity) {
                startActivity(intent)
                finish()
            }
        }

        fun toLoginActivity(activity: Activity) {
            val intent = Intent(activity, LoginActivity().javaClass)
            with(activity) {
                startActivity(intent)
                finish()
            }
        }

        fun upFragment(fragment: Fragment) {
            fragment.findNavController().navigateUp()
        }

        fun toRegisterFragment(loginFragment: LoginFragment) {
            loginFragment.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        fun toRegisterPickPreferenceFragment(registerFragment: RegisterFragment) {
            registerFragment.findNavController().navigate(R.id.action_registerFragment_to_registerPickPreferenceFragment)
        }

        fun toRegisterCarSetupFragment(registerPickPreferenceFragment: RegisterPickPreferenceFragment) {
            registerPickPreferenceFragment.findNavController().navigate(R.id.action_registerPickPreferenceFragment_to_registerCarSetupFragment)
        }

        fun toRegisterDidYouKnowFragment(registerPickPreferenceFragment: RegisterPickPreferenceFragment) {
            registerPickPreferenceFragment.findNavController().navigate(R.id.action_registerPickPreferenceFragment_to_registerDidYouKnowFragment)
        }

        fun toRegisterDidYouKnowFragment(registerCarSetupFragment: RegisterCarSetupFragment) {
            registerCarSetupFragment.findNavController().navigate(R.id.action_registerCarSetupFragment_to_registerDidYouKnowFragment)
        }

        fun toRegisterBeginJourneyFragment(registerDidYouKnowFragment: RegisterDidYouKnowFragment) {
            registerDidYouKnowFragment.findNavController().navigate(R.id.action_registerDidYouKnowFragment_to_registerBeginJourneyFragment)
        }

        fun toHomeFragment(myActivityFragment: MyActivityFragment) {
            myActivityFragment.findNavController().navigate(R.id.action_myActivityFragment_to_homeFragment)
        }

        fun toHomeFragment(accountFragment: AccountFragment) {
            accountFragment.findNavController().navigate(R.id.action_accountFragment_to_homeFragment)
        }

        fun toMyActivityFragment(homeFragment: HomeFragment) {
            homeFragment.findNavController().navigate(R.id.action_homeFragment_to_myActivityFragment)
        }

        fun toMyActivityFragment(accountFragment: AccountFragment) {
            accountFragment.findNavController().navigate(R.id.action_accountFragment_to_myActivityFragment)
        }

        fun toAccountFragment(homeFragment: HomeFragment) {
            homeFragment.findNavController().navigate(R.id.action_homeFragment_to_accountFragment)
        }

        fun toAccountFragment(myActivityFragment: MyActivityFragment) {
            myActivityFragment.findNavController().navigate(R.id.action_myActivityFragment_to_accountFragment)
        }
    }
}