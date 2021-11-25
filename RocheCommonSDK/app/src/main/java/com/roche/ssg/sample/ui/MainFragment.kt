package com.roche.ssg.sample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.roche.ssg.sample.R
import com.roche.ssg.sample.data.users
import com.roche.ssg.sample.databinding.FragmentMainBinding
import com.roche.ssg.sample.vm.UsersViewModel


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val usersViewModel: UsersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.item = usersViewModel.selectedUser
        setAllUsers()
        return binding.root
    }

    private fun setAllUsers() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            users.map { it.userName })
        binding.spinnerUsers.adapter = adapter
    }
}