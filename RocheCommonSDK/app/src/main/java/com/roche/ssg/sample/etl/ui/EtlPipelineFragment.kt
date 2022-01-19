package com.roche.ssg.sample.etl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.roche.ssg.sample.R
import com.roche.ssg.sample.databinding.FragmentEtlPipelineBinding
import com.roche.ssg.sample.etl.vm.EtlViewModel

class EtlPipelineFragment : Fragment() {

    private lateinit var mBinding: FragmentEtlPipelineBinding
    private val mEtlViewModel: EtlViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentEtlPipelineBinding.inflate(inflater, container, false)
        setLoginClickListener()
        setPreSignedUrlListener()
        setViewStateObserver()
        return mBinding.root
    }

    private fun setPreSignedUrlListener() {
        mBinding.btnGetPreSignedUrl.setOnClickListener {

            val tagCount = mBinding.etTagCount.text.toString().trim()
            if (tagCount.isEmpty())
                showMessage(getString(R.string.please_enter_tag_count))
            else {
                toggleProgressVisibility()
                mEtlViewModel.getPreSignedUrl(tagCount.toInt())
            }
        }
    }

    private fun setLoginClickListener() {
        mBinding.btnGetCognitoToken.setOnClickListener {
            if (mBinding.etUsername.text.toString().trim()
                    .isEmpty() || mBinding.etPassword.text.toString().trim().isEmpty()
            ) {
                showMessage(getString(R.string.please_enter_valid_credentials))
                return@setOnClickListener
            }
            toggleProgressVisibility()
            mEtlViewModel.login(
                mBinding.etUsername.text.toString().trim(),
                mBinding.etPassword.text.toString().trim()
            )
        }
    }

    private fun setViewStateObserver() {
        mEtlViewModel.etlStates.observe(viewLifecycleOwner, {
            toggleProgressVisibility()
            when (it.result) {
                is EtlViewModel.EtlResult.AmplifyError -> {
                    showMessage("Amplify initialization error")
                }
                is EtlViewModel.EtlResult.LoginSuccess -> {
                    showMessage("Login Successful")
                }
                is EtlViewModel.EtlResult.LoginFailed -> {
                    showMessage("Login Failed")
                }
                is EtlViewModel.EtlResult.SignedUrlFailed -> {
                    showMessage("Signed URL Failed ${it.result.error}")
                }
                is EtlViewModel.EtlResult.SignedUrlSuccess -> {
                    showMessage("Success ${it.result.response.url}")
                }
            }
        })
    }

    private fun showMessage(message: String) {
        mBinding.txtStatus.text = message
    }

    private fun toggleProgressVisibility() {
        mBinding.progressBar.progressBarHolder.isVisible =
            !mBinding.progressBar.progressBarHolder.isVisible
    }
}