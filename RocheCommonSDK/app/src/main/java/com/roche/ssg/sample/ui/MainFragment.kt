package com.roche.ssg.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.roche.ssg.sample.R
import com.roche.ssg.sample.databinding.FragmentMainBinding
import com.roche.ssg.sample.splitio.vm.SplitViewModel


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val splitViewModel: SplitViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.item = splitViewModel.selectedUser
        setAllUsers()
        return binding.root
    }

    private fun setAllUsers() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            splitViewModel.users.map { it.userName })
        binding.spinnerUsers.adapter = adapter
    }
}