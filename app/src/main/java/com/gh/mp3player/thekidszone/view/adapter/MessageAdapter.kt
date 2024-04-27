package com.gh.mp3player.thekidszone.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.model.MessageModel
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private var list: List<MessageModel>, private var context: Context) :
    RecyclerView.Adapter<MessageAdapter.MessageHolder>() {
        
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false)
        return MessageHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val message: MessageModel = list[position]
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email != null) {
            if (message.email==email){
                holder.lnYourLL.visibility=View.GONE
                holder.lnMyLL.visibility=View.VISIBLE
                holder.tvMyMes.text=message.mes
                holder.tvMyName.text=message.name
                Glide.with(context).load(message.link).into(holder.tvMyLink)
            }
            else{
                holder.lnYourLL.visibility=View.VISIBLE
                holder.lnMyLL.visibility=View.GONE
                holder.tvYourMes.text=message.mes
                holder.tvYourName.text=message.name
                Glide.with(context).load(message.link).into(holder.tvYourLink)
            }
}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMyName: TextView
        var tvMyLink: ImageView
        var tvMyMes: TextView
        var tvYourName: TextView
        var tvYourLink: ImageView
        var tvYourMes: TextView
        var lnMyLL: LinearLayout
        var lnYourLL: LinearLayout

        init {
            tvMyName = itemView.findViewById(R.id.myName)
            tvMyLink = itemView.findViewById(R.id.myLink)
            tvMyMes = itemView.findViewById(R.id.myMes)
            tvYourName = itemView.findViewById(R.id.yourName)
            tvYourLink = itemView.findViewById(R.id.yourLink)
            tvYourMes = itemView.findViewById(R.id.yourMes)
            lnMyLL=itemView.findViewById(R.id.myLL)
            lnYourLL=itemView.findViewById(R.id.yourLL)
        }
    }
}
