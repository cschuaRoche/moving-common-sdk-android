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
import com.roche.ssg.staticcontent.DownloadStaticContentResult
import com.roche.ssg.staticcontent.entity.StaticContentInfo
import kotlinx.coroutines.launch

class ReleaseLessMaterialFragment : Fragment() {
    private lateinit var binding: FragmentReleaselessMaterialBinding
    private lateinit var downloadStaticContent: DownloadStaticContent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReleaselessMaterialBinding.inflate(inflater, container, false)
        downloadStaticContent =
            DownloadStaticContent.getInstance(requireContext().applicationContext)
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.btnDownload.setOnClickListener {
            downloadStaticContent()
        }
    }

    private fun downloadStaticContent() {
        with(binding) {
            viewLoadingProgressBar.isVisible = true
            txtStatus.text = ""
            txtCancelDownloadStatus.text = ""
            lifecycleScope.launch {
                val staticContentInfo = StaticContentInfo(
                    manifestUrl = etManifestUrl.text.toString(),
                    appVersion = etAppVersion.text.toString(),
                    locale = etLocale.text.toString(),
                    fileKey = etFileType.text.toString(),
                    targetSubDir = etTargetSubdir.text.toString(),
                    allowWifiOnly = switchWifiOnly.isChecked
                )
                downloadStaticContent.downloadStaticAssets(
                    staticContentInfo,
                    ::downloadStaticContentCallback
                )
            }
        }
    }

    private fun downloadStaticContentCallback(result: DownloadStaticContentResult) {
        with(binding) {
            when (result) {
                is DownloadStaticContentResult.Success -> {
                    txtStatus.text = getString(R.string.downloaded_path, result.path)
                    viewLoadingProgressBar.isVisible = false
                }
                is DownloadStaticContentResult.Failure -> {
                    txtStatus.text = getString(R.string.downloaded_path, result.message)
                    viewLoadingProgressBar.isVisible = false
                }
                is DownloadStaticContentResult.DownloadProgress -> {
                    Log.d(LOG_TAG, "Downloading Progress: ${result.progress}")
                }
            }
        }
    }

    companion object {
        private const val LOG_TAG = "ReleaseLessMaterial"
    }
}