package com.roche.roche.dis.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.roche.roche.dis.UserManualViewModel
import com.roche.roche.dis.databinding.FragmentUtilsBinding
import com.roche.roche.dis.staticcontent.DownloadStaticContent
import com.roche.roche.dis.utils.UnZipUtils
import kotlinx.coroutines.launch
import java.io.File


class UtilsFragment : Fragment() {

    private lateinit var downloadViewModel: UserManualViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUtilsBinding.inflate(inflater, container, false)
        setClickEvent(binding)

        downloadViewModel = UserManualViewModel(requireActivity().application)

        return binding.root
    }

    private fun setClickEvent(binding: FragmentUtilsBinding) {
        binding.btnUnzip.setOnClickListener {
            unzipFile()
        }

        binding.btnUserManual.setOnClickListener {
            downloadStaticContent()
        }

        binding.btnIsRooted.setOnClickListener {

        }

    }

    private fun unzipFile() {
        var path: File = requireContext().filesDir
        val targetDirectory = "usermanuals"
        path = File(path.toString() + File.separator + targetDirectory)
        if (path.list().isNullOrEmpty()) {
            Log.d("files", "Creating files")
            UnZipUtils.unzipFromAsset("de_DE.zip", targetDirectory, requireContext())
        } else {
            Log.d("files", "Directory is not empty!")
            getFilesRecursive(path)
        }
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
                        "1.2.1",
                        DownloadStaticContent.LocaleType.EN_US,
                        ::showProgress
                    )
                Log.d("usermanual", "file path: $path")
            } catch (e: IllegalStateException) {
                Log.e("usermanual", "error: $e")
            } catch (e1: IllegalArgumentException) {
                Log.e("usermanual", "error: $e1")
            }
        }
    }

    private fun showProgress(progress: Int) {
        Log.d("usermanual", "Downloading Progress: $progress")
    }
}

