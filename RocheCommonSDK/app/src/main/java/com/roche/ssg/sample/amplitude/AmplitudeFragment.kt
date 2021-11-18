package com.roche.ssg.sample.amplitude

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amplitude.api.Amplitude
import com.roche.ssg.sample.databinding.FragmentSplitIoBinding


class AmplitudeFragment : Fragment() {

    private lateinit var binding: FragmentSplitIoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logEvent("screen_amplitude_experiments_open")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplitIoBinding.inflate(inflater, container, false)
        binding.isDataAvailable = true
        return binding.root
    }

    private fun logEvent(event: String) {
        try {
            val client = Amplitude.getInstance()
            client.userId = "User1"
            client.logEvent(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}