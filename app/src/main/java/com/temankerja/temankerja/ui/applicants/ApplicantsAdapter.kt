package com.temankerja.temankerja.ui.applicants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.temankerja.temankerja.databinding.ListApplicantBinding
import com.temankerja.temankerja.models.JobsApplicants

class ApplicantsAdapter() : RecyclerView.Adapter<ApplicantsAdapter.ListViewHolder>() {
    inner class ListViewHolder (val binding: ListApplicantBinding) : RecyclerView.ViewHolder(binding.root)

    private var applicantData = mutableListOf<JobsApplicants>()

    private var setOnItemClickCallback : OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.setOnItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            ListApplicantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setApplicantData(list: List<JobsApplicants>){
        applicantData.clear()
        applicantData = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val applicant = applicantData[position]
        holder.binding.tvName.text = applicant.user.fullname
        holder.binding.tvDesc.text = applicant.resume
        holder.binding.tvGender.text = "${applicant.user.gender} / 17"
        holder.binding.tvLoc.text = applicant.user.address
        if(applicant.user.photo != ""){
            Glide.with(holder.itemView.context)
                .load(applicant.user.photo)
                .into(holder.binding.imgApplicant)
        }

        holder.binding.root.setOnClickListener {
            setOnItemClickCallback?.onItemClicked(applicant)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: JobsApplicants)
    }

    override fun getItemCount(): Int {
        return applicantData.size
    }
}