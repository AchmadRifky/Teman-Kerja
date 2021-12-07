package com.temankerja.temankerja.ui.user.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.temankerja.temankerja.databinding.ListNotificationBinding
import com.temankerja.temankerja.models.Notification

class NotificationAdapter() : RecyclerView.Adapter<NotificationAdapter.ListViewHolder>() {
    inner class ListViewHolder (val binding: ListNotificationBinding) : RecyclerView.ViewHolder(binding.root)

    private var informationData = mutableListOf<Notification>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ListNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setNotificationData(list: List<Notification>){
        informationData.clear()
        informationData = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val info = informationData[position]
        holder.binding.tvNotification.text = "Selamat, Anda ${info.status} sebagai ${info.role} di ${info.company}"
    }

    override fun getItemCount(): Int {
        return informationData.size
    }
}