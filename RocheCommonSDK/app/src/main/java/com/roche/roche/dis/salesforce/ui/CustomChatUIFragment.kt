package com.roche.roche.dis.salesforce.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roche.roche.dis.databinding.CustomChatBinding
import com.roche.roche.dis.salesforce.viewmodel.CustomChatUIViewModel
import com.roche.roche.dis.salesforce.viewmodel.SalesforceChatViewModel

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