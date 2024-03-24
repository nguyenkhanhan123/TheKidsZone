package com.gh.mp3player.thekidszone.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gh.mp3player.thekidszone.R
import com.gh.mp3player.thekidszone.model.GroupModel

class GroupAdapter(private var list: List<GroupModel>, private var context: Context, private var event: View.OnClickListener) :
    RecyclerView.Adapter<GroupAdapter.GroupHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false)
        return GroupHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        val group: GroupModel = list[position]
        holder.tvNameGroup.text="Nhóm ${group.name}"
        holder.tvIDGroup.text="ID Nhóm: ${group.id}"
        holder.tvLeader.text="${group.leader}"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class GroupHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNameGroup: TextView
        var tvIDGroup: TextView
        var tvLeader: TextView

        init {
            tvNameGroup=itemView.findViewById(R.id.name_group)
            tvIDGroup=itemView.findViewById(R.id.id_group)
            tvLeader=itemView.findViewById(R.id.leader)
        }
    }
}
