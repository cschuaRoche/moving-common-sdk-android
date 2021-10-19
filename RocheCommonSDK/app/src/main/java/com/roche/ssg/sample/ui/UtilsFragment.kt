package com.roche.ssg.sample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roche.ssg.sample.R
import com.roche.ssg.sample.databinding.FragmentUtilsBinding
import com.roche.ssg.sample.security.utils.RootDetectUtil
import com.roche.ssg.sample.utils.UnZipUtils
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
}

