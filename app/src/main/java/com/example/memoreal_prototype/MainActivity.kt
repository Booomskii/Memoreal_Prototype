package com.example.memoreal_prototype

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.memoreal_prototype.room_db.MemorealDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var uname: String
    val client = UserSession.client
    val baseUrl = UserSession.baseUrl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        /*serverRequest()*/

        val username = findViewById<EditText>(R.id.editTextUname)
        val password = findViewById<EditText>(R.id.editTextPword)
        val submitButton = findViewById<Button>(R.id.btnSubmit)
        val loginGuest = findViewById<Button>(R.id.btnLoginGuest)
        val toSignUp = findViewById<TextView>(R.id.textViewSignUp)

        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPreferences = EncryptedSharedPreferences.create(
            this,
            "userSession",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        submitButton.setOnClickListener {
            uname = username.text.toString()
            val pword = password.text.toString()

            if (loginAuthenticator(uname, pword)) {
                // Send plain password to backend (no hashing on client side)
                userLogin(uname, pword) { success ->
                    runOnUiThread {  // Ensure this runs on the main thread
                        if (success) {
                            loginSuccess()
                            finish()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Invalid username or password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        loginGuest.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.putBoolean("isGuestUser", true)
            editor.apply()

            val intent = Intent(applicationContext, HomePageActivity::class.java)
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

    private fun loginAuthenticator(username: String, password: String): Boolean {
        return when {
            username.isEmpty() -> {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter your Email!",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            password.isEmpty() -> {
                Toast.makeText(
                    this@MainActivity,
                    "Please enter your Password!",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            else -> {
                true
            }
        }
    }

    private fun userLogin(username: String, password: String, callback: (Boolean) -> Unit) {
        val url = baseUrl + "api/login"

        // Create JSON object with plain password
        val jsonObject = JSONObject().apply {
            put("username", username)
            put("password", password)
        }

        val json = jsonObject.toString()

        // Log the JSON for debugging
        Log.d("LoginRequest", "JSON Body: $json")

        val body = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                Log.d("Login", "Response Code: ${response.code}")
                Log.d("Login", "Response Body: $responseBody")
                if (response.isSuccessful) {
                    val jsonResponse = JSONObject(responseBody ?: "{}")
                    if (jsonResponse.has("accessToken")) {
                        val accessToken = jsonResponse.getString("accessToken")
                        saveToken(accessToken)
                        callback(true)
                    } else {
                        Log.e("Login", "Access token not found in response")
                        callback(false)
                    }
                } else if (response.code == 401) {
                    callback(false)
                } else {
                    callback(false)
                }
            }
        })
    }

    private fun loginSuccess(){
        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // Initialize class-level sharedPreferences
        sharedPreferences = EncryptedSharedPreferences.create(
            this,
            "userSession",  // File name
            masterKey,      // Master key for encryption
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.putBoolean("isGuestUser", false)
        editor.putString("username", uname)
        editor.apply()

        val intent = Intent(this, HomePageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun saveToken(token: String) {
        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // Initialize class-level sharedPreferences
        sharedPreferences = EncryptedSharedPreferences.create(
            this,
            "userSession",  // File name
            masterKey,      // Master key for encryption
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val editor = sharedPreferences.edit()
        editor.putString("accessToken", token)
        editor.apply()

        // Log the stored token to ensure it was saved
        val storedToken = sharedPreferences.getString("accessToken", null)
        Log.d("StoredToken", "Stored Access Token: $storedToken")
    }

}