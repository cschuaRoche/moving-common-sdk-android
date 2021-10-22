package com.roche.ssg.sample.salesforce.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roche.ssg.sample.databinding.CustomChatBinding
import com.roche.ssg.sample.salesforce.viewmodel.CustomChatUIViewModel

class CustomChatUIFragment : Fragment() {

    private lateinit var binding: CustomChatBinding
    private lateinit var viewModel: CustomChatUIViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = CustomChatUIViewModel(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CustomChatBinding.inflate(inflater, container, false)
        setClickEvent()

        return binding.root
    }

    private fun setClickEvent() {
        binding.btnChat.setOnClickListener {
            viewModel.startChat(requireActivity())
        }
    }
}