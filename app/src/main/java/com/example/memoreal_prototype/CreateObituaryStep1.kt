package com.example.memoreal_prototype

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONObject

class CreateObituaryStep1 : Fragment() {
    private var isPlanSelected = false
    val client = UserSession.client
    val baseUrl = UserSession.baseUrl

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
            createObituaryStep2.arguments = bundle

            (activity as HomePageActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, createObituaryStep2)
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_out_left, R.anim.slide_out_right)
                .addToBackStack("CreateObituaryStep1")
                .commit()
        }

        (activity as HomePageActivity).showBottomNavigation()

        return view
    }

    private fun registerUser(user: com.example.memoreal_prototype.models.User, password: String) {
        val url = baseUrl + "api/addUser"

        // Create a JSON object to send to the server
        val json = JSONObject().apply {
            put("FIRST_NAME", user.FIRST_NAME)
            put("LAST_NAME", user.LAST_NAME)
            put("MI", user.MI)
            put("USERNAME", user.USERNAME)
            put("CONTACT_NUMBER", user.CONTACT_NUMBER)
            put("EMAIL", user.EMAIL)
            put("BIRTHDATE", user.BIRTHDATE)
            put("PICTURE", user.PICTURE)
            put("PASSWORD", password)  // Include the plain text password
        }.toString()

        val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("RegisterUser", "Request failed: ${e.message}")
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Registration failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Show success message
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "User registered successfully", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Parse the response to get the error message
                    val errorBody = response.body?.string()
                    val errorMessage = try {
                        // Try to extract the "message" from the JSON response
                        val jsonError = JSONObject(errorBody ?: "")
                        jsonError.getString("message")
                    } catch (e: Exception) {
                        "Registration failed: Unknown error"
                    }

                    // Show the error message as a toast
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}