package com.temankerja.temankerja.ui.user.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.temankerja.temankerja.R
import com.temankerja.temankerja.databinding.ListHomeBinding
import com.temankerja.temankerja.models.Sertifikasi
import com.temankerja.temankerja.databinding.ListSertifikasiBinding
import com.temankerja.temankerja.models.Jobs

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {
    inner class ListViewHolder (val binding: ListHomeBinding) : RecyclerView.ViewHolder(binding.root)

    private var jobsData = mutableListOf<Jobs>()

    private var setOnItemClickCallback : OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.setOnItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ListHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setJobsData(list: List<Jobs>){
        jobsData.clear()
        jobsData = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val job = jobsData[position]
        holder.binding.homeName.text = job.jobsTitle
        holder.binding.homeLocation.text = job.jobsLoc
        holder.binding.homeDesc.text = job.jobsDesc
        holder.binding.homePrice.text = job.jobsSalary
        if(job.photo!=""){
            Glide.with(holder.itemView.context)
                .load(job.photo)
                .into(holder.binding.imgBanner)
        }else{
            Glide.with(holder.itemView.context)
                .load(holder.binding.root.context.getDrawable(R.drawable.work))
                .into(holder.binding.imgBanner)
        }
        holder.binding.root.setOnClickListener {
            setOnItemClickCallback?.onItemClicked(job)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Jobs)
    }

    override fun getItemCount(): Int {
        return jobsData.size
    }
}