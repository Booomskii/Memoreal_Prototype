package com.example.memoreal_prototype

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.memoreal_prototype.db.MemorealDatabase
import com.example.memoreal_prototype.db.UserViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Log.i("MYTAG","MainActivity:OnCreate")
            val username = findViewById<EditText>(R.id.editTextUname)
            val password = findViewById<EditText>(R.id.editTextPword)
            val submitButton = findViewById<Button>(R.id.btnSubmit)
            val loginGuest = findViewById<Button>(R.id.btnLoginGuest)
            val toSignUp = findViewById<TextView>(R.id.textViewSignUp)
            var enteredUsername = ""
            var pword = ""
            submitButton.setOnClickListener {
                enteredUsername = username.text.toString()
                pword = password.text.toString()

                lifecycleScope.launch {
                    if (loginAuthenticator(enteredUsername, pword)){
                        val sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isLoggedIn", true)
                        editor.putBoolean("isGuestUser", false)
                        editor.putString("username", enteredUsername)
                        editor.apply()

                        val intent = Intent(applicationContext,HomePageActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            loginGuest.setOnClickListener {
                val sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.putBoolean("isGuestUser", true)
                editor.apply()
                val intent = Intent(applicationContext,HomePageActivity::class.java)
                startActivity(intent)
            }

        toSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private suspend fun loginAuthenticator(username: String, password: String): Boolean {
        val dao = MemorealDatabase.getInstance(application).userDao()

        return when {
            username.isEmpty() -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter your Email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                false
            }
            password.isEmpty() -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter your Password!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                false
            }
            else -> {
                val user = withContext(Dispatchers.IO) {
                    dao.getUserByUsername(username)
                }
                if (user != null && user.password == password) {
                    true
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@MainActivity,
                            "Invalid username or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    false
                }
            }
        }
    }


    /*override fun onStart() {
        super.onStart()
        Log.i("MYTAG","MainActivity:OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MYTAG","MainActivity:OnResume")
    }

    override fun onPause(){
        super.onPause()
        Log.i("MYTAG","MainActivity:OnPause")
    }

    override fun onStop(){
        super.onStop()
        Log.i("MYTAG","MainActivity:OnStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("MYTAG","MainActivity:OnDestroy")
    }

    override fun onRestart(){
        super.onRestart()
        Log.i("MYTAG","MainActivity:OnRestart")
    }*/
}