package com.example.memoreal_prototype

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.memoreal_prototype.db.MemorealDatabase
import com.example.memoreal_prototype.db.User
import com.example.memoreal_prototype.db.UserViewModelFactory
import kotlinx.coroutines.launch
import java.util.Calendar


class SignUpActivity2 : AppCompatActivity() {

    private lateinit var uploadImg: ImageView
    private lateinit var imageUri: Uri
    private lateinit var image: String
    private lateinit var viewModel: UserViewModel

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            uploadImg.setImageURI(uri)
            imageUri = uri
            image = imageUri.toString()
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        val text = findViewById<TextView>(R.id.textViewPersonalInfo)
        val username = intent.getStringExtra("username")
        val skip = findViewById<TextView>(R.id.textViewSkip)
        val cont = findViewById<Button>(R.id.btnContinue)
        val back = findViewById<ImageView>(R.id.imgBack)
        val firstName = findViewById<EditText>(R.id.editTextFirstName)
        val lastName = findViewById<EditText>(R.id.editTextLastName)
        val middleInitial = findViewById<EditText>(R.id.editTextMI)
        val contactNum = findViewById<EditText>(R.id.editTextNumber)
        val birthDate = findViewById<EditText>(R.id.editTextDate)
        val dao = MemorealDatabase.getInstance(application).userDao()
        val factory = UserViewModelFactory(dao)
        viewModel = ViewModelProvider(this,factory).get(UserViewModel::class.java)
        /*val genderSpinner = findViewById<Spinner>(R.id.spinnerGender)
        val genderItems = listOf("Select Gender", "Male", "Female", "Transgender", "Non-binary", "Genderfluid", "Cisgender")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderItems)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = genderAdapter*/

        /*genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "Select Gender") {
                    Toast.makeText(this@SignUpActivity2, "Select Gender", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle selected gender
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(this@SignUpActivity2, "Select Gender", Toast.LENGTH_SHORT).show()
            }
        }*/

        uploadImg = findViewById(R.id.imageViewUploadPic)
        uploadImg.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        birthDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Update the editText with the selected date
                    birthDate.setText(String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear))
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        cont.setOnClickListener {
            val fname = firstName.text.toString()
            val lname = lastName.text.toString()
            val mi = middleInitial.text.toString()
            val contact = contactNum.text.toString()
            val bdate = birthDate.text.toString()

            Log.d("SignUpActivity2", "Starting validation")
            if (inputValidator(fname, lname, mi, bdate, contact/*, image*/)) {
                Log.d("SignUpActivity2", "Input validated")
                lifecycleScope.launch {
                    try {
                        Log.d("SignUpActivity2", "Attempting to fetch user by username: $username")
                        val existingUser = dao.getUserByUsername(username!!.toString())

                        if (existingUser != null) {
                            Log.d("SignUpActivity2", "User found, updating: ${existingUser.username}")

                            viewModel.updateUser(
                                User(
                                    existingUser.userID,
                                    fname,
                                    lname,
                                    mi,
                                    username,
                                    existingUser.password,
                                    contact,
                                    existingUser.email,
                                    bdate,
                                    null
                                )
                            )
                            Log.d("SignUpActivity2", "User updated successfully")
                        } else {
                            Log.d("SignUpActivity2", "User not found!")
                        }
                    } catch (e: Exception) {
                        Log.e("SignUpActivity2", "Error during update: ${e.message}")
                    }
                }
                val intent = Intent(applicationContext, HomePageActivity::class.java)
                Log.d("SignUpActivity2", "Navigating to HomePageActivity")
                startActivity(intent)
            } else {
                Log.d("SignUpActivity2", "Input validation failed")
                Toast.makeText(
                    this@SignUpActivity2,
                    "There's something wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        skip.setOnClickListener{
            text.text = username.toString()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun inputValidator(fname:String?, lname:String?, mi:String?, bdate:String?, contact:String?/*, image:String*/): Boolean {
        return when {
            fname.isNullOrEmpty() -> {
                Toast.makeText(
                    this@SignUpActivity2,
                    "Enter your First Name",
                    Toast.LENGTH_SHORT
                    ).show()
                return false
            }
            lname.isNullOrEmpty() -> {
                Toast.makeText(
                    this@SignUpActivity2,
                    "Enter your Last Name",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            mi.isNullOrEmpty() -> {
                Toast.makeText(
                    this@SignUpActivity2,
                    "Enter your Middle Initial",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            contact.isNullOrEmpty() -> {
                Toast.makeText(
                    this@SignUpActivity2,
                    "Enter your Contact Number",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            bdate.isNullOrEmpty() -> {
                Toast.makeText(
                    this@SignUpActivity2,
                    "Enter your Birthdate",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            /*imageUri == null -> {
                Toast.makeText(
                    this@SignUpActivity2,
                    "Please upload an image",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }*/
            else -> {
                return true
            }
        }
    }

    override fun onBackPressed() {
        Toast.makeText(
            this@SignUpActivity2,
            "Complete the sign up process first or click 'Skip'",
            Toast.LENGTH_SHORT
            ).show()
    }
}
