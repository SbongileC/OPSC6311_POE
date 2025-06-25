package com.example.open_source_coding_part_2

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate

class HomeActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private lateinit var switchTheme: Switch

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)


            prefs = getSharedPreferences("settings", MODE_PRIVATE)
            val darkModeOn = prefs.getBoolean("dark_mode", false)

            // Apply saved theme before super.onCreate and setContentView
            AppCompatDelegate.setDefaultNightMode(
                if (darkModeOn) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )


            switchTheme = findViewById(R.id.switchTheme)
            switchTheme.isChecked = darkModeOn

            switchTheme.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                prefs.edit().putBoolean("dark_mode", isChecked).apply()
            }

            val btnsignup = findViewById<Button>(R.id.btnsignup)
            btnsignup?.setOnClickListener {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }

            val btnlogin = findViewById<Button>(R.id.btnlogin)
            btnlogin?.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

        }
    }
