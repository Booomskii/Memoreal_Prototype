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


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Log.i("MYTAG","MainActivity:OnCreate")
            val greetingTextView = findViewById<TextView>(R.id.textViewMemoreal)
            val inputField = findViewById<EditText>(R.id.editTextUname)
            val password = findViewById<EditText>(R.id.editTextPword)
            val submitButton = findViewById<Button>(R.id.btnSubmit)
            val offersButton =  findViewById<Button>(R.id.btnOffers)
            val toSignUp = findViewById<TextView>(R.id.textViewSignUp)
            var enteredUsername = ""
            var pword = ""
            submitButton.setOnClickListener {
                enteredUsername = inputField.text.toString()
                pword = password.text.toString()

                if (enteredUsername == "") {
                    offersButton.visibility = INVISIBLE
                    greetingTextView.text = ""
                    Toast.makeText(
                        this@MainActivity,  // Context
                        "Please enter your Email!",  // Text
                        Toast.LENGTH_SHORT // Duration
                    ).show()
                }else if (pword == ""){
                    Toast.makeText(
                        this@MainActivity,  // Context
                        "Please enter your Password!",  // Text
                        Toast.LENGTH_SHORT // Duration
                    ).show()
                }else{
                    val message = "Welcome $enteredUsername"
                    greetingTextView.text = message
                    inputField.text.clear()
                    offersButton.visibility = VISIBLE
                }
            }

        offersButton.setOnClickListener {
            val intent = Intent(this,SecondActivity::class.java)
            intent.putExtra("USER",enteredUsername)
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

    override fun onStart() {
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
    }
}