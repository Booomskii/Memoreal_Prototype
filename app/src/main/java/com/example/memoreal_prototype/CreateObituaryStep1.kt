package com.example.memoreal_prototype

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat

class CreateObituaryStep1 : Fragment() {

    private var isPlanSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_obituary_step1, container, false)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val backButton = toolbar.findViewById<ImageView>(R.id.backButton)
        val proceed = view.findViewById<Button>(R.id.btnProceed)

        val radioButton1 = view.findViewById<RadioButton>(R.id.radioButton1)
        val radioButton2 = view.findViewById<RadioButton>(R.id.radioButton2)
        val radioButton3 = view.findViewById<RadioButton>(R.id.radioButton3)
        val radioButton4 = view.findViewById<RadioButton>(R.id.radioButton4)
        val radioButtons = listOf(radioButton2, radioButton3, radioButton4)
        val bundle = Bundle()
        bundle.putBoolean("isRadioButton1Checked", radioButton1.isChecked)
        bundle.putBoolean("isRadioButton2Checked", radioButton2.isChecked)
        bundle.putBoolean("isRadioButton3Checked", radioButton3.isChecked)
        bundle.putBoolean("isRadioButton4Checked", radioButton4.isChecked)
        val colorStateList = ContextCompat.getColorStateList(requireContext(), R.color.radio_button_selector)

        radioButton1.buttonTintList = colorStateList
        radioButton2.buttonTintList = colorStateList
        radioButton3.buttonTintList = colorStateList
        radioButton4.buttonTintList = colorStateList

        for (radioButton in radioButtons) {
            radioButton.setOnClickListener {
                if (radioButton.isChecked && isPlanSelected) {
                    // If the clicked radio button is already checked, deselect it
                    radioButton.isChecked = false
                    isPlanSelected = false

                    // Reset the bundle
                    bundle.clear()
                } else {
                    // Uncheck all other radio buttons
                    for (rb in radioButtons) {
                        rb.isChecked = false
                    }
                    // Check the clicked radio button
                    radioButton.isChecked = true
                    isPlanSelected = true

                    // Update the bundle with the new selection
                    when (radioButton.id) {
                        R.id.radioButton1 -> {
                            bundle.putBoolean("isRadioButton1Checked", true)
                        }
                        R.id.radioButton2 -> {
                            bundle.putBoolean("isRadioButton2Checked", true)
                        }
                        R.id.radioButton3 -> {
                            bundle.putBoolean("isRadioButton3Checked", true)
                        }
                        R.id.radioButton4 -> {
                            bundle.putBoolean("isRadioButton4Checked", true)
                        }
                    }
                }
            }
        }

        backButton.setOnClickListener {
            (activity as HomePageActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, Home())
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_out_left, R.anim.slide_out_right)
                .commit()
        }

        proceed.setOnClickListener {
            val createObituaryStep2 = CreateObituaryStep2()
            createObituaryStep2.arguments = bundle // Pass the bundle here

            (activity as HomePageActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, createObituaryStep2) // Use the new instance with the bundle
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_out_left, R.anim.slide_out_right)
                .addToBackStack("CreateObituaryStep1")
                .commit()
        }


        // Access Home Page Activity and show bottom navigation
        (activity as HomePageActivity).showBottomNavigation()

        return view
    }
}