package com.roche.ssg.sample.amplitude

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.amplitude.api.Amplitude
import com.roche.ssg.sample.R
import com.roche.ssg.sample.amplitude.vm.AmplitudeViewModel
import com.roche.ssg.sample.data.users
import com.roche.ssg.sample.databinding.FragmentSplitIoBinding
import com.roche.ssg.sample.vm.UsersViewModel


class AmplitudeFragment : Fragment() {

    private lateinit var binding: FragmentSplitIoBinding
    private val usersViewModel: UsersViewModel by activityViewModels()
    private val amplitudeViewModel: AmplitudeViewModel by viewModels()

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

        binding.isDataAvailable = false

        binding.btnA.setOnClickListener {
            Toast.makeText(context, "Enabled Button A..", Toast.LENGTH_LONG).show()
        }
        binding.btnB.setOnClickListener {
            Toast.makeText(context, "Enabled Button B..", Toast.LENGTH_LONG).show()
        }
        binding.btnC.setOnClickListener {
            Toast.makeText(context, "Enabled Button C..", Toast.LENGTH_LONG).show()
        }
        binding.btnD.setOnClickListener {
            Toast.makeText(context, "Enabled Button D..", Toast.LENGTH_LONG).show()
        }

        val currentUser = users[usersViewModel.selectedUser.position]

        // Set title
        binding.user.text = currentUser.userName
        // Set the values
        binding.tvVersion1.text = getString(R.string.split_io_version, "1.0.0")
        binding.tvVersion2.text = getString(R.string.split_io_version, "1.1.0")
        binding.tvStudy.text = getString(R.string.split_io_study)
        binding.tvCountry.text = getString(R.string.split_io_country)

        observeAmplitudeData()
        return binding.root
    }

    private fun observeAmplitudeData() {

        amplitudeViewModel.initClient(usersViewModel.selectedUser).observe(viewLifecycleOwner, {
            if (it) {
                binding.isDataAvailable = true
                setFlagVersionOneZero()
                setFlagVersionOneOne()
                setFlagUnitedStatesUsers()
                setFlagStudy()
                setExperimentPercentTest()
                setExperimentSignUpCreateAccount()
                setConfiguration()
            }
        })
    }

    private fun logEvent(event: String) {
        try {
            val client = Amplitude.getInstance()
            client.userId = users[usersViewModel.selectedUser.position].userID
            client.logEvent(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setFlagVersionOneZero() {
        if (amplitudeViewModel.getFlagVersionOneZero()) {
            binding.btnA.text = getString(R.string.enabled)
            binding.btnA.isEnabled = true
        }
    }

    private fun setFlagVersionOneOne() {
        if (amplitudeViewModel.getFlagVersionOneOne()) {
            binding.btnB.text = getString(R.string.enabled)
            binding.btnB.isEnabled = true
        }
    }

    private fun setFlagUnitedStatesUsers() {
        if (amplitudeViewModel.getFlagUnitedStatesUsers()) {
            binding.btnC.text = getString(R.string.enabled)
            binding.btnC.isEnabled = true
        }
    }

    private fun setFlagStudy() {
        if (amplitudeViewModel.getFlagStudy()) {
            binding.btnD.text = getString(R.string.enabled)
            binding.btnD.isEnabled = true
        }
    }

    private fun setExperimentPercentTest() {
        if (amplitudeViewModel.getExperimentPercentTest()) {
            binding.btnE.text = getString(R.string.enabled)
            binding.btnE.isEnabled = true
        }
    }

    private fun setExperimentSignUpCreateAccount() {
        val experiment = amplitudeViewModel.getExperimentSignUpCreateAccount()
        binding.btnF.isEnabled = true
        binding.btnF.text = experiment?.text
        val color = Color.parseColor(experiment?.color)
        binding.btnF.backgroundTintList = ColorStateList.valueOf(color)
        //binding.btnF.setTextColor(Color.parseColor(experiment.textcolor))
    }

    private fun setConfiguration() {
        binding.userConfiguration.text = amplitudeViewModel.getAllConfiguration().toString()
    }
}