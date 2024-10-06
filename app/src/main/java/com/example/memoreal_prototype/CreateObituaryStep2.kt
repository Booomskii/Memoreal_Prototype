package com.example.memoreal_prototype

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class CreateObituaryStep2 : Fragment() {

    private var isRadioButton1Checked: Boolean = false
    private var isRadioButton2Checked: Boolean = false
    private var isRadioButton3Checked: Boolean = false
    private var isRadioButton4Checked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_obituary_step2, container, false)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val backButton = toolbar.findViewById<ImageView>(R.id.backButton)
        val payNow = view.findViewById<Button>(R.id.btnPayNow)
        val planTextView = view.findViewById<TextView>(R.id.tvPlan)
        val creationFeeTextView = view.findViewById<TextView>(R.id.tvCreationFee)
        val planFeeTextView = view.findViewById<TextView>(R.id.tvSubscriptionFee)
        val totalTV = view.findViewById<TextView>(R.id.tvTotalAmount)
        var creationFee = 0.0f
        creationFee = getString(R.string.memorialCreationFeeDec).toFloat()
        val formattedCreationFee = "₱${"%.2f".format(creationFee)}"
        var planFee = 0.0f
        val bundle = Bundle()

        arguments?.let {
            isRadioButton1Checked = it.getBoolean("isRadioButton1Checked", false)
            isRadioButton2Checked = it.getBoolean("isRadioButton2Checked", false)
            isRadioButton3Checked = it.getBoolean("isRadioButton3Checked", false)
            isRadioButton4Checked = it.getBoolean("isRadioButton4Checked", false)

            // Log the received values
            Log.d("NextFragment", "RadioButton1: $isRadioButton1Checked")
            Log.d("NextFragment", "RadioButton2: $isRadioButton2Checked")
            Log.d("NextFragment", "RadioButton3: $isRadioButton3Checked")
            Log.d("NextFragment", "RadioButton4: $isRadioButton4Checked")
        }

        val isRadioButton2Checked = arguments?.getBoolean("isRadioButton2Checked") ?: false
        val isRadioButton3Checked = arguments?.getBoolean("isRadioButton3Checked") ?: false
        val isRadioButton4Checked = arguments?.getBoolean("isRadioButton4Checked") ?: false

        if (isRadioButton2Checked) {
            planTextView.text = getString(R.string.basic)
            planFeeTextView.text = "₱${"%.2f".format(getString(R.string.basicFeeDec).toFloat())}"
            planFee = getString(R.string.basicFeeDec).toFloat()
            bundle.putString("plan", planTextView.text.toString())
        }
        if (isRadioButton3Checked) {
            planTextView.text = getString(R.string.premium)
            planFeeTextView.text = "₱${"%.2f".format(getString(R.string.premiumFeeDec).toFloat())}"
            planFee = getString(R.string.premiumFeeDec).toFloat()
            bundle.putString("plan", planTextView.text.toString())
        }
        if (isRadioButton4Checked) {
            planTextView.text = getString(R.string.lifetime)
            planFeeTextView.text = "₱${"%.2f".format(getString(R.string.lifetimeFeeDec).toFloat())}"
            planFee = getString(R.string.lifetimeFeeDec).toFloat()
            bundle.putString("plan", planTextView.text.toString())
        }
        if (!isRadioButton2Checked && !isRadioButton3Checked && !isRadioButton4Checked){
            planTextView.text = getString(R.string.noPlan)
            planFeeTextView.text = getString(R.string.zero)
        }


        creationFeeTextView.text = formattedCreationFee
        val total = creationFee + planFee
        totalTV.text = "₱${"%.2f".format(total)}".format(total)

        backButton.setOnClickListener {
            (activity as HomePageActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, Home())
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_out_left, R.anim.slide_out_right)
                .commit()
        }

        payNow.setOnClickListener {
            val createObituaryStep22 = CreateObituaryStep2_2()
            createObituaryStep22.arguments = bundle

            (activity as HomePageActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, createObituaryStep22)
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_out_left, R.anim.slide_out_right)
                .addToBackStack("CreateObituaryStep2")
                .commit()
        }

        (activity as HomePageActivity).showBottomNavigation()

        return view
    }
}