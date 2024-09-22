package com.example.memoreal_prototype

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class UserSession: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val isGuestUser = sharedPreferences.getBoolean("isGuestUser", false)
        val username = sharedPreferences.getString("username", "")

        if (!isLoggedIn) {
            // If the user is not logged in, redirect to the login screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Prevent the user from returning to this activity
        }
    }
}