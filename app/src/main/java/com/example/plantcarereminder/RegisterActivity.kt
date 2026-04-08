package com.example.plantcarereminder

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {

            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields ❌", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(name = name, email = email, password = password)

            Thread {
                userDao.registerUser(user)

                runOnUiThread {
                    Toast.makeText(this, "Registered Successfully 🎉", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }
    }
}