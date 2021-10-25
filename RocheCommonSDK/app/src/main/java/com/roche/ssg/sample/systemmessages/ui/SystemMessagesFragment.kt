package com.roche.ssg.sample.systemmessages.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.roche.dis.systemmessages.SystemMessages
import com.roche.dis.systemmessages.data.api.RetrofitApiService
import com.roche.dis.systemmessages.data.model.SystemMessage
import com.roche.ssg.sample.R
import com.roche.ssg.sample.databinding.BottomSheetSystemMessageBinding
import com.roche.ssg.sample.databinding.FragmentSystemMessagesBinding
import kotlinx.coroutines.launch

class SystemMessagesFragment : Fragment() {

    private lateinit var binding: FragmentSystemMessagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSystemMessagesBinding.inflate(inflater, container, false)
        setRequestSystemMessagesListener()
        return binding.root
    }

    private fun setRequestSystemMessagesListener() {
        with(binding) {
            btnRequestSystemMessages.setOnClickListener {
                progressBar.progressBarHolder.isVisible = true
                txtError.isVisible = false
                lifecycleScope.launch {
                    try {
                        val systemMessages = getSystemMsgsFromInput()
                        if (systemMessages.isNotEmpty()) {
                            systemMessages.forEach {
                                showSystemMessageDialog(it.type, it.defaultMessage, it.resourceId)
                            }
                        } else {
                            txtError.text = getString(R.string.system_messages_not_available)
                            txtError.isVisible = true
                        }
                    } catch (e: RetrofitApiService.ApiException) {
                        txtError.text = getString(R.string.error, "${e.statusCode} - ${e.message}")
                        txtError.isVisible = true
                    } catch (e1: Exception) {
                        txtError.text = getString(R.string.error, e1.message)
                        txtError.isVisible = true
                    }
                    progressBar.progressBarHolder.isVisible = false
                }
            }
        }
    }

    private suspend fun getSystemMsgsFromInput(): List<SystemMessage> {
        with(binding) {
            return SystemMessages.getSystemMessages(
                context = requireContext(),
                baseUrl = etUrl.text.toString(),
                messageTypeList = getMessageTypes(),
                appOrSamdId = etAppSamdId.text.toString(),
                appOrSamdVersion = etAppSamdVersion.text.toString(),
                country = etCountry.text.toString().takeIf { etCountry.text.isNullOrEmpty().not() }
            )
        }
    }

    private fun getMessageTypes(): List<String> {
        val text = binding.etType.text.toString()
        val pattern = Regex(".*[\\s\\S]*[^,\\s\$]")
        val finalText = pattern.find(text)?.value ?: ""
        return finalText.split(",").map { it.trim() }
    }

    private fun showSystemMessageDialog(title: String, message: String, resourceId: String) {
        val dialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                // do nothing
            }.setNegativeButton(getString(R.string.do_not_show_again)) { _, _ ->
                handleDismissSystemMessage(resourceId)
            }.create()
        dialog.show()
    }

    private fun showSystemMessageBottomDialog(title: String, message: String, resourceId: String) {
        val layout = BottomSheetSystemMessageBinding.inflate(layoutInflater)

        val dialog = BottomSheetDialog(requireContext())
        dialog.setCancelable(false)
        dialog.setContentView(layout.root)

        layout.txtTitle.text = title
        layout.txtMessage.text = message
        layout.txtOkay.setOnClickListener {
            // do nothing
        }
        layout.txtDoNotShow.setOnClickListener {
            handleDismissSystemMessage(resourceId)
        }
        dialog.show()
    }

    private fun handleDismissSystemMessage(resourceId: String) {
        SystemMessages.dismissMessage(requireContext(), resourceId)
    }
}