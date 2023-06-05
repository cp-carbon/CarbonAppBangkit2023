package com.example.carbonapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carbonapp.HomeOnNavigationItemSelected
import com.example.carbonapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var newsAdapter: HomeNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        newsAdapter = HomeNewsAdapter()
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
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        val rv = v.findViewById<RecyclerView>(R.id.h_rv_twitterpost)
        rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
        val fab = v.findViewById<ExtendedFloatingActionButton>(R.id.h_fab)
        fab.setOnClickListener {
            HomeAddActivityBottomSheetDialogFragment().show(parentFragmentManager, "bottom_sheet_add_activity")
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNav = view.rootView.findViewById<BottomNavigationView>(R.id.bottom_nav_home)
        bottomNav.setOnItemSelectedListener(HomeOnNavigationItemSelected(this))
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }

    private fun onStateUpdate(state: HomeUiState) {
        if (state.isLoading) {
            Toast.makeText(context, resources.getString(R.string.loading), Toast.LENGTH_SHORT).show()
            return
        }

        if (state.hasError) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
            return
        }

        newsAdapter.updateData(state.news)
    }
}