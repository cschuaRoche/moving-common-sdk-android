package com.roche.ssg.sample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.roche.ssg.sample.R
import com.roche.ssg.sample.databinding.FragmentReleaselessMaterialBinding
import com.roche.ssg.staticcontent.DownloadStaticContent
import kotlinx.coroutines.launch

class ReleaseLessMaterialFragment : Fragment() {
    private lateinit var binding: FragmentReleaselessMaterialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReleaselessMaterialBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.btnDownload.setOnClickListener {
            downloadStaticContent()
        }
        binding.btnCancelDownload.setOnClickListener {
            cancelDownload()
        }
    }

    private fun downloadStaticContent() {
        with(binding) {
            viewLoadingProgressBar.isVisible = true
            txtStatus.text = ""
            txtCancelDownloadStatus.text = ""
            lifecycleScope.launch {
                txtStatus.text = try {
                    val path = DownloadStaticContent.downloadStaticAssets(
                        context = requireContext(),
                        manifestUrl = etManifestUrl.text.toString(),
                        appVersion = etAppVersion.text.toString(),
                        locale = etLocale.text.toString(),
                        fileKey = etFileType.text.toString(),
                        progress = ::showProgress,
                        targetSubDir = etTargetSubdir.text.toString(),
                        allowWifiOnly = switchWifiOnly.isChecked
                    )
                    getString(R.string.downloaded_path, path)
                } catch (e: Exception) {
                    getString(R.string.error, e.message)
                }
                viewLoadingProgressBar.isVisible = false
            }
        }
    }

    private fun cancelDownload() {
        with(binding) {
            DownloadStaticContent.cancelDownload(
                context = requireContext(),
                appVersion = etAppVersion.text.toString(),
                locale = etLocale.text.toString(),
                fileKey = etFileType.text.toString(),
                targetSubDir = etTargetSubdir.text.toString(),
                callback = ::cancelDownloadCallback
            )
        }
    }

    private fun showProgress(progress: Int) {
        Log.d(LOG_TAG, "Downloading Progress: $progress")
    }

    private fun cancelDownloadCallback(status: Boolean) {
        with(binding) {
            txtCancelDownloadStatus.text = if (status) {
                getString(R.string.txt_cancel_succeeded)
            } else {
                getString(R.string.txt_cancel_failed)
            }
            viewLoadingProgressBar.isVisible = false
        }
    }

    companion object {
        private const val LOG_TAG = "ReleaseLessMaterial"
    }
}