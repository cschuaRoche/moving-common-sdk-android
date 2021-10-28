package com.roche.ssg.sample.push.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.roche.ssg.pushnotification.PushNotificationException
import com.roche.ssg.sample.R
import com.roche.ssg.sample.databinding.FragmentPushRegisterBinding
import com.roche.ssg.sample.push.vm.PushNotificationViewModel

class RegisterPushFragment : Fragment() {

    private lateinit var binding: FragmentPushRegisterBinding

    private val mPushViewModel: PushNotificationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPushRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLoginListener()
        setRegisterListener()
        setUnregisterListener()
        setViewStateObserver()
    }

    private fun setViewStateObserver() {
        mPushViewModel.pushNotificationStates.observe(viewLifecycleOwner, {
            toggleProgressVisibility()
            when (it.result) {
                is PushNotificationViewModel.PushNotificationResult.AmplifyError -> {
                    showMessage("Amplify initialization error")
                }
                is PushNotificationViewModel.PushNotificationResult.LoginSuccess -> {
                    binding.btnRegister.isEnabled = true
                    binding.btnUnregister.isEnabled = true
                    showMessage("Login Successful")
                }
                is PushNotificationViewModel.PushNotificationResult.LoginFailed -> {
                    binding.btnRegister.isEnabled = false
                    binding.btnUnregister.isEnabled = false
                    showMessage("Login Failed")
                }
                is PushNotificationViewModel.PushNotificationResult.RegistrationSuccess -> {
                    showMessage("Registration successful ${it.result.response}")
                }
                is PushNotificationViewModel.PushNotificationResult.RegistrationFailed -> {
                    val ex = it.result.error
                    if (ex is PushNotificationException) {
                        showMessage("Registration Failed with status ${ex.status}")
                    } else {
                        showMessage("Registration Failed")
                    }
                }
                is PushNotificationViewModel.PushNotificationResult.DeRegistrationSuccess -> {
                    showMessage("Un-registration Successful ${it.result.response}")
                }
                is PushNotificationViewModel.PushNotificationResult.DeRegistrationFailed -> {
                    val ex = it.result.error
                    if (ex is PushNotificationException) {
                        showMessage("Un-registration Failed with status ${ex.status}")
                    } else {
                        showMessage("Un-registration Failed")
                    }
                }
            }
        })
    }


    private fun setLoginListener() {
        binding.btnGetCognitoToken.setOnClickListener {
            toggleProgressVisibility()
            mPushViewModel.login()
        }
    }

    private fun setRegisterListener() {
        binding.btnRegister.setOnClickListener {
            if (mPushViewModel.areNotificationsEnabled(NotificationManagerCompat.from(requireContext()))) {
                toggleProgressVisibility()
                mPushViewModel.registerDevice()
            } else {
                showPushNotEnableDialog(
                    getString(R.string.push_dialog_title),
                    getString(R.string.push_dialog_message)
                )
            }
        }
    }

    private fun setUnregisterListener() {
        binding.btnUnregister.setOnClickListener {
            toggleProgressVisibility()
            mPushViewModel.deregisterDevice()
        }
    }

    private fun showMessage(message: String) {
        binding.txtStatus.text = message
    }

    private fun toggleProgressVisibility() {
        binding.progressBar.progressBarHolder.isVisible =
            !binding.progressBar.progressBarHolder.isVisible
    }

    private fun showPushNotEnableDialog(title: String, message: String) {
        val dialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                // do nothing
            }.create()
        dialog.show()
    }
}