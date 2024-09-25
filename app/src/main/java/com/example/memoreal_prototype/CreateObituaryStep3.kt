package com.example.memoreal_prototype

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import org.w3c.dom.Text
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CreateObituaryStep3 : Fragment() {

    private lateinit var uploadImg: ImageView
    private var imageUri: Uri? = null
    private lateinit var image: String
    private lateinit var viewModel:UserViewModel

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            uri: Uri? ->
        if (uri != null) {
            uploadImg.setImageURI(uri)
            imageUri = uri
            image = imageUri.toString()
        } else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_obituary_step3, container, false)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val backButton = toolbar.findViewById<ImageView>(R.id.backButton)
        val nextButton = view.findViewById<Button>(R.id.btnNext)
        val fullNameET = view.findViewById<EditText>(R.id.etFullName)
        val dateBirthET = view.findViewById<EditText>(R.id.etDateBirth)
        val datePassingET = view.findViewById<EditText>(R.id.etDatePassing)
        val biographyET = view.findViewById<EditText>(R.id.etBiography)

        uploadImg = view.findViewById(R.id.ivUploadPic)
        uploadImg.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        dateBirthET.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Update the editText with the selected date
                    dateBirthET.setText(String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear))
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        datePassingET.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Update the editText with the selected date
                    datePassingET.setText(String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear))
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        backButton.setOnClickListener {
            (activity as HomePageActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, Home())
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_out_left, R.anim.slide_out_right)
                .commit()
        }

        nextButton.setOnClickListener {
            val fullName = fullNameET.text.toString()
            val dateBirthStr = dateBirthET.text.toString()
            val datePassingStr = datePassingET.text.toString()
            val biography = biographyET.text.toString()

            if (inputValidator(fullName, dateBirthStr, datePassingStr, biography)){
                val dateBirth = parseDate(dateBirthET.text.toString(),"Date of birth")
                val datePassing = parseDate(datePassingET.text.toString(), "Date of passing")
                if (dateValidator(dateBirth!!, datePassing!!)){
                    Toast.makeText(
                        requireContext(),
                        "Date of Birth cannot be greater than Date of Passing",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    (activity as HomePageActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, CreateObituaryStep4())
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_out_left, R.anim.slide_out_right)
                        .addToBackStack("CreateObituaryStep3")
                        .commit()
                }
            }
        }

        return view
    }

    private fun inputValidator(fullName:String, dateBirth: String, datePassing: String,
                               biography:
    String) : Boolean{
        return when {
            fullName.isNullOrEmpty() -> {
                Toast.makeText(requireContext(),
                    "Enter the person's full name",
                    Toast.LENGTH_SHORT).show()
                return false
            }
            dateBirth.isNullOrEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    "Enter the person's date of birth",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            datePassing.isNullOrEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    "Enter the person's date of passing",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            biography.isNullOrEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    "Enter the person's biography",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

    fun parseDate(dateStr: String, fieldName: String): Date? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return if (dateStr.isNotEmpty()) {
            try {
                dateFormat.parse(dateStr)
            } catch (e: ParseException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Invalid $fieldName format", Toast.LENGTH_SHORT).show()
                null
            }
        } else {
            Toast.makeText(requireContext(), "$fieldName is required", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun dateValidator(dateBirth: Date, datePassing: Date): Boolean {
        val currentDate = Date()
        if (dateBirth.after(currentDate)) {
            return false
        }
        if (datePassing.before(dateBirth)) {
            return false
        }
        return true
    }
}