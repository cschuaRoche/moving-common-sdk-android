package com.roche.roche.dis.systemmessages.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.roche.roche.dis.R
import com.roche.roche.dis.databinding.BottomSheetSystemMessageBinding
import com.roche.roche.dis.databinding.FragmentSystemMessagesBinding

class SystemMessagesFragment : Fragment() {


    private lateinit var binding: FragmentSystemMessagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSystemMessagesBinding.inflate(inflater, container, false)
        setRequestSystemMessagesListener()
        setClearCacheListener()
        return binding.root
    }

    private fun setRequestSystemMessagesListener() {
        binding.btnRequestSystemMessages.setOnClickListener {
            // TODO call getMessageTypes() to get message types
        }
    }

    private fun getMessageTypes(): List<String> {
        val text = binding.etType.text.toString()
        val pattern = Regex(".*[\\s\\S]*[^,\\s\$]")
        val finalText = pattern.find(text)?.value ?: ""
        return finalText.split(",").map { it.trim() }
    }

    private fun setClearCacheListener() {
        binding.btnClearCache.setOnClickListener {
            // TODO clear cache
        }
    }

    private fun showSystemMessageDialog(title: String, message: String) {
        val dialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                TODO("Handle Okay click here")
            }.setNegativeButton(getString(R.string.do_not_show_again)) { dialog, which ->
                TODO("Handle do not show again click here")
            }.create()
        dialog.show()
    }

    private fun showSystemMessageBottomDialog(title: String, message: String) {
        val layout = BottomSheetSystemMessageBinding.inflate(layoutInflater)

        val dialog = BottomSheetDialog(requireContext())
        dialog.setCancelable(false)
        dialog.setContentView(layout.root)

        layout.txtTitle.text = title
        layout.txtMessage.text = message
        layout.txtOkay.setOnClickListener {
            TODO("Handle Okay click here")
        }
        layout.txtDoNotShow.setOnClickListener {
            TODO("Handle do not show again click here")
        }
        dialog.show()
    }
}