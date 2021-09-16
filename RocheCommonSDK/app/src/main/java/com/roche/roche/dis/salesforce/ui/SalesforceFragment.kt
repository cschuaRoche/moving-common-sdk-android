package com.roche.roche.dis.salesforce.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.roche.roche.dis.R
import com.roche.roche.dis.databinding.FragmentSalesforceBinding
import com.roche.roche.dis.salesforce.viewmodel.SalesforceChatViewModel

class SalesforceFragment : Fragment() {

    private lateinit var binding: FragmentSalesforceBinding
    private lateinit var viewModel: SalesforceChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = SalesforceChatViewModel(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSalesforceBinding.inflate(inflater, container, false)
        setClickEvent()

        return binding.root
    }

    private fun setClickEvent() {
        binding.btnChat.setOnClickListener {
            viewModel.startChat(requireActivity())
        }
        binding.btnChatFull.setOnClickListener{
            viewModel.startChatInFullScreenMode(requireActivity())
        }
        binding.btnChatCustom.setOnClickListener {
            findNavController().navigate(R.id.action_to_custom_chat_ui)
        }
    }
}