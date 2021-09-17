package com.roche.roche.dis.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.roche.roche.dis.R
import com.roche.roche.dis.databinding.FragmentReleaselessMaterialBinding
import com.roche.roche.dis.staticcontent.DownloadStaticContent
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
    }

    private fun downloadStaticContent() {
        with(binding) {
            txtStatus.text = ""
            lifecycleScope.launch {
                txtStatus.text = try {
                    val path = DownloadStaticContent.downloadStaticAssets(
                        requireContext(),
                        MANIFEST_URL,
                        etAppVersion.text.toString(),
                        etLocale.text.toString(),
                        etFileType.text.toString(),
                        ::showProgress,
                        allowWifiOnly = switchWifiOnly.isChecked
                    )
                    getString(R.string.downloaded_path, path)
                } catch (e: Exception) {
                    getString(R.string.error, e.message)
                }
            }
        }
    }

    private fun showProgress(progress: Int) {
        Log.d(LOG_TAG, "Downloading Progress: $progress")
    }

    companion object {
        private const val LOG_TAG = "ReleaseLessMaterial"
        private const val MANIFEST_URL =
            "https://static-content.dhp-dev.dhs.platform.navify.com/com.roche.floodlight/docs/floodlight.json"
    }
}