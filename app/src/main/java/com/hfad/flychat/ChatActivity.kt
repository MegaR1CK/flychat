package com.hfad.flychat

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setSupportActionBar(toolbar_chat)

        val user = Firebase.auth.currentUser
        title = user?.displayName

        val messagesTable = Firebase.database.reference.child("messages")
        messages_recycler.layoutManager = LinearLayoutManager(this)
        pb_chat.visibility = View.VISIBLE
        messagesTable.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pb_chat.visibility = View.INVISIBLE
                val messages = mutableListOf<MessageModel>()
                for (child in snapshot.children) {
                    messages.add(child.getValue(MessageModel::class.java) ?:
                    MessageModel("", "", 0))
                }
                messages_recycler.adapter = MessagesAdapter(messages)
                messages_recycler.scrollToPosition(messages.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                pb_chat.visibility = View.INVISIBLE
                App.errorAlert(error.message, this@ChatActivity)
            }
        })

        messages_recycler.setOnTouchListener { v, event ->
            v.performClick()
            field_send.clearFocus()
            false
        }

        field_send.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                btn_send.isEnabled = s?.isNotBlank() == true
            }
        })

        btn_send.setOnClickListener {
            val message = MessageModel(field_send.text.toString(),
                user?.displayName ?: "",
                Date().time)
            messagesTable.push().setValue(message)
            field_send.text.clear()
        }
        btn_send.isEnabled = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            Firebase.auth.signOut()
            if (!App.WAS_AUTH) finish()
            else startActivity(Intent(this, LoginActivity::class.java))
        }
        return true
    }
}