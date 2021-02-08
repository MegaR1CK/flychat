package com.hfad.flychat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        if (auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        btn_login.setOnClickListener {
            val email = field_email.text.toString()
            val password = field_password.text.toString()
            if (email.isNotBlank() && password.isNotBlank()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        startActivity(Intent(this, HomeActivity::class.java))
                    }
                    .addOnFailureListener {
                        it.message?.let { it1 -> App.errorAlert(it1, this) }
                    }
            }
            else App.errorAlert(getString(R.string.error_empty_field), this)
        }

        btn_to_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}