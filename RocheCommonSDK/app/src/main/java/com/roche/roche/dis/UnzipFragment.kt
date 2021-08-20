package com.roche.roche.dis

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.roche.roche.dis.databinding.FragmentUnzipBinding
import com.roche.roche.dis.utils.UnZipUtils
import java.io.File


class UnzipFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUnzipBinding.inflate(inflater, container, false)
        setClickEvent(binding)
        return binding.root
    }

    private fun setClickEvent(binding: FragmentUnzipBinding) {
        binding.btnUnzip.setOnClickListener {
            unzipFile()
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
}

