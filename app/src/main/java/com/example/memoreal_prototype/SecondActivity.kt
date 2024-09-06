package com.example.memoreal_prototype

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        Log.i("MYTAG","SecondActivity:OnCreate")
        val username = intent.getStringExtra("USER")
        val textView = findViewById<TextView>(R.id.textViewOffer)
        val message = "$username, you will get free access to all the content for one month."
        textView.text = message

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("MYTAG","SecondActivity:OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MYTAG","SecondActivity:OnResume")
    }

    override fun onPause(){
        super.onPause()
        Log.i("MYTAG","SecondActivity:OnPause")
    }

    override fun onStop(){
        super.onStop()
        Log.i("MYTAG","SecondActivity:OnStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("MYTAG","SecondActivity:OnDestroy")
    }

    override fun onRestart(){
        super.onRestart()
        Log.i("MYTAG","SecondActivity:OnRestart")
    }
}