package com.example.plantcarereminder

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin) // 🔥 must match XML
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter all fields ❌", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Thread {
                val user = userDao.login(email, password)

                runOnUiThread {
                    if (user != null) {
                        Toast.makeText(this, "Login Success 🎉", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Invalid Credentials ❌", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}