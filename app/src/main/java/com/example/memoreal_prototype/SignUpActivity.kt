package com.example.memoreal_prototype

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.memoreal_prototype.db.User
import com.example.memoreal_prototype.db.MemorealDatabase
import com.example.memoreal_prototype.db.UserDao
import com.example.memoreal_prototype.db.UserViewModelFactory
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    private lateinit var emailAdd: EditText
    private lateinit var username:EditText
    private lateinit var sf:SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var viewModel:UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        val toLogin = findViewById<ImageView>(R.id.imgBack)
        val createAcc = findViewById<Button>(R.id.btnCreateAcc)
        emailAdd = findViewById(R.id.editTextEmailAddress)
        username = findViewById(R.id.editTextUsername)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val conpassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val agree = findViewById<CheckBox>(R.id.checkBoxTerms)
        sf = getSharedPreferences("signup_sf",MODE_PRIVATE)
        editor = sf.edit()

        val dao = MemorealDatabase.getInstance(application).userDao()
        val factory = UserViewModelFactory(dao)
        viewModel = ViewModelProvider(this,factory).get(UserViewModel::class.java)

        toLogin.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        createAcc.setOnClickListener {
            val uname = username.text.toString()
            val pword = password.text.toString()
            val email = emailAdd.text.toString()
            val conpword = conpassword.text.toString()
            /*
            concise alternative this one
            viewModel.insertUser(
                User(
                    0,
                    username.text.toString(),
                    password.text.toString(),
                    emailAdd.text.toString(),
                )
            )*/
            if (inputValidator(email, uname, pword, conpword, agree)){
                lifecycleScope.launch {
                    val checkUName = dao.duplicateUser1(uname)
                    val checkEmail = dao.duplicateUser2(email)
                    if (checkUName) {
                        Toast.makeText(
                            this@SignUpActivity,
                            "User already exists",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (checkEmail) {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Email is already taken",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val user = User(0, null, null, null, uname, pword, null, email,
                            null, null)
                        viewModel.insertUser(user)
                        val intent = Intent(applicationContext, SignUpActivity2::class.java)
                        intent.putExtra("username", uname)
                        startActivity(intent)
                        val textFields = listOf(emailAdd, username, password, conpassword)
                        textFields.forEach { it.text.clear() }
                    }
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun inputValidator(emailAd:String?, username:String?, password:String?,
                               conpassword:String?, agree: CheckBox): Boolean {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

        return when {
            emailAd.isNullOrEmpty() -> {
                Toast.makeText(
                    this@SignUpActivity,
                    "Enter your Email Address",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            !emailAd.matches(emailRegex) -> {
                Toast.makeText(
                    this@SignUpActivity,
                    "Enter a valid Email Address with '@' and domain name",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            username.isNullOrEmpty() -> {
                Toast.makeText(
                    this@SignUpActivity,
                    "Enter your Username",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            password.isNullOrEmpty() -> {
                Toast.makeText(
                    this@SignUpActivity,
                    "Enter your Password",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            conpassword.isNullOrEmpty() || conpassword != password -> {
                Toast.makeText(
                    this@SignUpActivity,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            (!passwordValidator(password)) -> {
                Toast.makeText(
                    this@SignUpActivity,
                    "Password must be at least 8 characters long, contains one " +
                            "uppercase letter, one " +
                            "number, and one special character!",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            !agree.isChecked -> {
                Toast.makeText(
                    this@SignUpActivity,
                    "Please agree to the Terms and Conditions",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun passwordValidator(password:String): Boolean{
        val minLength = password.length >= 8
        val hasUpperCase = password.any {it.isUpperCase()}
        val hasNumber = password.any {it.isDigit()}
        val hasSpecChar = password.any {it.isSpecialChar()}
        return minLength && hasUpperCase && hasNumber && hasSpecChar
    }

    private fun Char.isSpecialChar(): Boolean {
        return !this.isLetterOrDigit() && !this.isWhitespace()
    }

    override fun onPause(){
        super.onPause()
        val email = emailAdd.text.toString()
        val uname = username.text.toString()
        editor.apply{
            putString("sf_email", email)
            putString("sf_uname", uname)
            commit()
        }
    }

    override fun onResume(){
        super.onResume()
        val email = sf.getString("sf_email", null)
        val uname = sf.getString("sf_uname", null)
        emailAdd.setText(email)
        username.setText(uname)
    }
}