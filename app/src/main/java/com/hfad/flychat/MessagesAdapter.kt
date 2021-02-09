package com.hfad.flychat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_message.view.*
import java.text.SimpleDateFormat
import java.util.*

class MessagesAdapter(val messages: List<MessageModel>) :
    RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    inner class ViewHolder(val container: ConstraintLayout) : RecyclerView.ViewHolder(container)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent,
            false) as ConstraintLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.container
        val message = messages[position]
        view.mes_name.text = message.user
        view.mes_text.text = message.text
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        view.mes_time.text = sdf.format(Date(message.date))
    }

    override fun getItemCount() = messages.size
}