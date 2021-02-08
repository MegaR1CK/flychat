package com.hfad.flychat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        lateinit var defaultAvatar: Uri
        val ref = FirebaseStorage.getInstance().reference.child("default_avatar.jpg")
        ref.downloadUrl.addOnSuccessListener {
            defaultAvatar = it
        }

        btn_register.setOnClickListener {
            val email = field_email_reg.text.toString()
            val name = field_name_reg.text.toString()
            val password = field_password_reg.text.toString()
            val confPassword = field_confirm_password_reg.text.toString()
            if (email.isNotBlank() && name.isNotBlank() &&
                password.isNotBlank() && confPassword.isNotBlank()) {
                if (password == confPassword) {
                    Firebase.auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra(HomeActivity.NAME_KEY, name)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            it.message?.let { it1 -> App.errorAlert(it1, this) }
                        }
                    }
                else App.errorAlert(getString(R.string.error_password_confirm), this)
                }
            else App.errorAlert(getString(R.string.error_empty_field), this)
        }
    }
}
