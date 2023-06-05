package com.example.carbonapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.carbonapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeAddActivityBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.bottom_sheet_add_activity, container, false)

        val eatingOption = v.findViewById<CardView>(R.id.bsad_eating_option)
        eatingOption.setOnClickListener {
            // TODO: Handle on select eating
        }
        val travelingOption = v.findViewById<CardView>(R.id.bsad_traveling_option)
        travelingOption.setOnClickListener {
            // TODO: Handle on select traveling
        }
        val otherOption = v.findViewById<CardView>(R.id.bsad_custom_option)
        otherOption.setOnClickListener {
            // TODO: Handle on select other
        }

        return v
    }
}