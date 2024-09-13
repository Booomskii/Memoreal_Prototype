package com.example.memoreal_prototype

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.memoreal_prototype.databinding.ActivityHomePageBinding

class HomePageActivity : UserSession() {

    private lateinit var binding : ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)

        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> replaceFragment(Home())
                R.id.nav_explore -> replaceFragment(Explore())
                R.id.nav_obituaries -> replaceFragment(MyObituaries())
                R.id.nav_profile -> replaceFragment(Profile())
                else -> {}
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        // Show a dialog to confirm logout
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                // Clear the login session
                val sharedPreferences = getSharedPreferences("userSession", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear() // Clear all session data
                editor.apply()

                // Go back to login screen
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()  // Close HomePageActivity
            }
            .setNegativeButton("No", null)
            .show()
    }

}