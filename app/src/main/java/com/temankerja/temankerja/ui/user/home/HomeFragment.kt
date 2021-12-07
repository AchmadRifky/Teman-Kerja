package com.temankerja.temankerja.ui.user.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.temankerja.temankerja.databinding.FragmentHomeBinding
import com.temankerja.temankerja.models.Jobs
import com.temankerja.temankerja.ui.user.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { HomeAdapter() }
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRv()
        activity?.let {
            runBlocking {
                viewModel.jobsList.observe(viewLifecycleOwner, {
                    it.data?.let { data -> adapter.setJobsData(data) }
                })
            }
        }
        adapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Jobs) {
                selectedData(data)
            }
        })
        binding.textInputSearch.addTextChangedListener {
            viewModel.searchNameChanged(it.toString())
            binding.rvLowongan.adapter!!.notifyDataSetChanged()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun selectedData(data: Jobs) {
        Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_JOB, data)
            startActivity(this)
        }
    }

    private fun setupRv() {
        binding.rvLowongan.adapter = adapter
        binding.rvLowongan.layoutManager = LinearLayoutManager(context)
        binding.rvLowongan.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}