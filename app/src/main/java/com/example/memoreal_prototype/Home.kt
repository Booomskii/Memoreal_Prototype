package com.example.memoreal_prototype

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class Home : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val btnCreateObituary = view.findViewById<Button>(R.id.btnCreateObituary)
        val btnBrowse = view.findViewById<Button>(R.id.buttonBrowse)

        btnCreateObituary.setOnClickListener {
            /*val intent = Intent(activity, CreateObituaryStep1Activity::class.java)
            startActivity(intent)*/
        }

        return view
    }
}