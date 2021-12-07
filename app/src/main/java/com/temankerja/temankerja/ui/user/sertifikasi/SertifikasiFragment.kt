package com.temankerja.temankerja.ui.user.sertifikasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.temankerja.temankerja.databinding.FragmentSertifikasiBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.temankerja.temankerja.R
import com.temankerja.temankerja.models.Sertifikasi


class SertifikasiFragment : Fragment() {
    private var _binding : FragmentSertifikasiBinding? = null
    private val binding get() = _binding!!
    private var listSertifikasi : ArrayList<Sertifikasi> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSertifikasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvSertifikasi.setHasFixedSize(true)
        listSertifikasi.addAll(getSertifikasi())
        showRecyclerList()
        super.onViewCreated(view, savedInstanceState)
    }

    fun getSertifikasi() : ArrayList<Sertifikasi> {
        val title = resources.getStringArray(R.array.sertifikasi_title)
        val desc = resources.getStringArray(R.array.sertifikasi_desc)
        val photo = resources.obtainTypedArray(R.array.sertifikasi_photo)
        val listSertifikasiTemp = ArrayList<Sertifikasi>()
        for (position in title.indices) {
            val sertifikasi = Sertifikasi(
                title[position],
                desc[position],
                photo.getResourceId(position, -1)
            )
            listSertifikasiTemp.add(sertifikasi)
        }
        return listSertifikasiTemp
    }

    private fun showRecyclerList() {
        binding.rvSertifikasi.layoutManager = LinearLayoutManager(context)
        val adapter = SertifikasiAdapter(listSertifikasi)
        binding.rvSertifikasi.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}