package com.roche.roche.dis.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.roche.roche.dis.R
import com.roche.roche.dis.databinding.FragmentUtilsBinding
import com.roche.roche.dis.security.utils.RootDetectUtil
import com.roche.roche.dis.staticcontent.DownloadStaticContent
import com.roche.roche.dis.utils.UnZipUtils
import kotlinx.coroutines.launch
import java.io.File


class UtilsFragment : Fragment() {

    private lateinit var binding: FragmentUtilsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUtilsBinding.inflate(inflater, container, false)
        setClickEvent()

        return binding.root
    }

    private fun setClickEvent() {
        binding.btnUnzip.setOnClickListener {
            binding.statusUnzip = ""
            unzipFile()
        }

        binding.btnUserManual.setOnClickListener {
            binding.statusUserManual = ""
            downloadStaticContent()
        }

        binding.btnIsRooted.setOnClickListener {
            binding.statusRooted = ""
            if (RootDetectUtil.isDeviceRooted())
                binding.statusRooted = getString(R.string.status_true)
            else
                binding.statusRooted = getString(R.string.status_false)
        }

    }

    private fun unzipFile() {
        var path: File = requireContext().filesDir
        val targetDirectory = "usermanuals"
        path = File(path.toString() + File.separator + targetDirectory)
        if (path.list().isNullOrEmpty()) {
            Log.d("files", "Creating files")
            UnZipUtils.unzipFromAsset("de_DE.zip", requireContext(), targetDirectory)
        } else {
            Log.d("files", "Directory is not empty!")
            getFilesRecursive(path)
        }
        binding.statusUnzip = path.absolutePath
    }

    private fun getFilesRecursive(pFile: File) {
        for (files in pFile.listFiles()) {
            if (files.isDirectory) {
                getFilesRecursive(files)
            } else {
                Log.d("files", "$files")
            }
        }
    }

    private fun downloadStaticContent() {
        lifecycleScope.launch {
            try {
                val path =
                    DownloadStaticContent.downloadStaticAssets(
                        requireContext(),
                        "https://passport-static-content.tpp1-dev.platform.navify.com/com.roche.nrm_passport/docs/floodlight.json",
                        "1.2.0",
                        DownloadStaticContent.LocaleType.EN_US,
                        "user-manuals",
                        ::showProgress
                    )
                Log.d("usermanual", "file path: $path")
                binding.statusUserManual = path
            } catch (e: IllegalStateException) {
                Log.e("usermanual", "error: $e")
                binding.statusUserManual = getString(R.string.error)
            } catch (e1: IllegalArgumentException) {
                Log.e("usermanual", "error: $e1")
                binding.statusUserManual = getString(R.string.error)
            } catch (e2: Exception) {
                e2.printStackTrace()
                binding.statusUserManual = getString(R.string.error)
            }
        }
    }

    private fun showProgress(progress: Int) {
        Log.d("usermanual", "Downloading Progress: $progress")
    }
}

