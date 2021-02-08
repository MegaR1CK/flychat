package com.hfad.flychat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object { val NAME_KEY = "NAME_KEY" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (intent.extras != null) {
            tv.text = intent.extras?.getString(NAME_KEY)
        }
        else {
            tv.text = Firebase.auth.currentUser?.email
        }
    }
}