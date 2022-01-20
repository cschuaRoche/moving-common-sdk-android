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
        with(binding) {
            btnDownload1.setOnClickListener {
                viewLoadingProgressBar1.isVisible = true
                txtStatus1.text = ""
                downloadStaticContent(etFileType1.text.toString())
            }
            btnDownload2.setOnClickListener {
                viewLoadingProgressBar2.isVisible = true
                txtStatus2.text = ""
                downloadStaticContent(etFileType2.text.toString())
            }
            btnDownload3.setOnClickListener {
                viewLoadingProgressBar3.isVisible = true
                txtStatus3.text = ""
                downloadStaticContent(etFileType3.text.toString())
            }
        }
    }

    private fun downloadStaticContent(fileType: String) {
        lifecycleScope.launch {
            val staticContentInfo = StaticContentInfo(
                manifestUrl = getString(R.string.static_content_base_url),
                appVersion = getString(R.string.static_content_version),
                locale = getString(R.string.static_content_locale),
                fileKey = fileType,
                targetSubDir = "SSG",
                allowWifiOnly = false
            )
            downloadStaticContent.downloadStaticAssets(
                staticContentInfo,
                ::downloadStaticContentCallback
            )
        }
    }

    private fun downloadStaticContentCallback(result: DownloadStaticContentResult) {
        when (result) {
            is DownloadStaticContentResult.Success -> {
                processSuccess(result.staticContentInfo.fileKey, result.path)
            }
            is DownloadStaticContentResult.Failure -> {
                processFailure(result.staticContentInfo.fileKey, result.message)
            }
            is DownloadStaticContentResult.DownloadProgress -> {
                Log.d(
                    LOG_TAG,
                    "Downloading Progress for ${result.staticContentInfo.fileKey}: ${result.progress}%"
                )
            }
        }
    }

    private fun processSuccess(fileType: String, path: String) {
        with(binding) {
            when (fileType) {
                etFileType1.text.toString() -> {
                    txtStatus1.text = getString(R.string.downloaded_path, path)
                    viewLoadingProgressBar1.isVisible = false
                }

                etFileType2.text.toString() -> {
                    txtStatus2.text = getString(R.string.downloaded_path, path)
                    viewLoadingProgressBar2.isVisible = false
                }

                etFileType3.text.toString() -> {
                    txtStatus3.text = getString(R.string.downloaded_path, path)
                    viewLoadingProgressBar3.isVisible = false
                }
            }
        }
    }

    private fun processFailure(fileType: String, message: String) {
        with(binding) {
            when (fileType) {
                etFileType1.text.toString() -> {
                    txtStatus1.text = getString(R.string.error, message)
                    viewLoadingProgressBar1.isVisible = false
                }

                etFileType2.text.toString() -> {
                    txtStatus2.text = getString(R.string.error, message)
                    viewLoadingProgressBar2.isVisible = false
                }

                etFileType3.text.toString() -> {
                    txtStatus3.text = getString(R.string.error, message)
                    viewLoadingProgressBar3.isVisible = false
                }
            }
        }
    }

    companion object {
        private const val LOG_TAG = "ReleaseLessMaterial"
    }
}