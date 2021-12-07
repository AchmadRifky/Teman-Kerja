package com.temankerja.temankerja.ui.user.informasi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ListHomeBinding
import com.temankerja.temankerja.databinding.ListInformasiBinding
import com.temankerja.temankerja.models.Information
import com.temankerja.temankerja.models.Jobs

class InformasiAdapter() : RecyclerView.Adapter<InformasiAdapter.ListViewHolder>() {
    inner class ListViewHolder (val binding: ListInformasiBinding) : RecyclerView.ViewHolder(binding.root)

    private var informationData = mutableListOf<Information>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ListInformasiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setInformationData(list: List<Information>){
        informationData.clear()
        informationData = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val info = informationData[position]
        holder.binding.informasiTitle.text = info.title
        holder.binding.informasiDesc.text = info.desc
        Glide.with(holder.itemView.context)
            .load(info.img)
            .into(holder.binding.informasiImg)
    }

    override fun getItemCount(): Int {
        return informationData.size
    }
}