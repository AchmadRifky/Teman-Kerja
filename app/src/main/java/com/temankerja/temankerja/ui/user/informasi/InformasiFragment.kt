package com.temankerja.temankerja.ui.user.informasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.temankerja.temankerja.databinding.FragmentInformasiBinding
import com.temankerja.temankerja.ui.user.home.HomeAdapter
import com.temankerja.temankerja.ui.user.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformasiFragment : Fragment() {

    private var _binding : FragmentInformasiBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { InformasiAdapter() }
    private val viewModel: InformasiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInformasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRv()
        activity?.let {
            viewModel.data.observe(it, {
                it.data?.let { data -> adapter.setInformationData(data) }
            })
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRv() {
        binding.rvInformation.adapter = adapter
        binding.rvInformation.layoutManager = LinearLayoutManager(context)
        binding.rvInformation.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}