package com.temankerja.temankerja.ui.user.sertifikasi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.temankerja.temankerja.models.Sertifikasi
import com.temankerja.temankerja.databinding.ListSertifikasiBinding

class SertifikasiAdapter(private val listSertifikasi: ArrayList<Sertifikasi>) : RecyclerView.Adapter<SertifikasiAdapter.ListViewHolder>() {
    inner class ListViewHolder (val binding: ListSertifikasiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ListSertifikasiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val sertifikasi = listSertifikasi[position]
        holder.binding.sertifikasiTitle.text = sertifikasi.title
        holder.binding.sertifikasiDesc.text = sertifikasi.desc
        Glide.with(holder.itemView.context)
            .load(sertifikasi.photo)
            .into(holder.binding.sertifikasiImg)
//        holder.itemView.setOnClickListener {
//
//        }
    }

    override fun getItemCount(): Int {
        return listSertifikasi.size
    }
}