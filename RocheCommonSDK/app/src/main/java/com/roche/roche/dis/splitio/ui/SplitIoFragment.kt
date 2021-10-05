package com.roche.roche.dis.splitio.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.roche.roche.dis.R
import com.roche.roche.dis.databinding.FragmentSplitIoBinding
import com.roche.roche.dis.splitio.vm.SplitViewModel


class SplitIoFragment : Fragment() {

    private lateinit var binding: FragmentSplitIoBinding

    private val splitViewModel: SplitViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplitIoBinding.inflate(inflater, container, false)

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

        val currentUser = splitViewModel.users[splitViewModel.selectedUser.position]

        // Set title
        binding.user.text = currentUser.userName
        // Set the values
        binding.tvVersion1.text = getString(R.string.split_io_version, "1.0.0")
        binding.tvVersion2.text = getString(R.string.split_io_version, "1.1.0")
        binding.tvStudy.text = getString(R.string.split_io_study)
        binding.tvCountry.text = getString(R.string.split_io_country)

        observeSplitData()
        return binding.root
    }

    private fun observeSplitData() {
        splitViewModel.initClient().observe(viewLifecycleOwner, {
            if (it) {
                setVersionTreatment()
                setCountryTreatment()
                setStudyTreatment()
                setRolloutTreatment()
                setStyleTreatment()
                setConfiguration()
            }
        })
    }

    private fun setConfiguration() {
        binding.userConfiguration.text = splitViewModel.getAllConfiguration().toString()
    }

    private fun setVersionTreatment() {
        val treatment = splitViewModel.getVersionTreatment()
        when {
            treatment.equals("Green_button") -> {
                // insert Green_button code here
                binding.btnA.isEnabled = true
                binding.btnA.text = getString(R.string.enabled)
            }
            treatment.equals("Red_button") -> {
                // insert Red_button code here
                binding.btnB.isEnabled = true
                binding.btnB.text = getString(R.string.enabled)
            }
            else -> {
                // insert control code here
                unsupportedTreatment(SplitViewModel.SPLIT_SSG_APP_VERSION)
            }
        }
    }

    private fun setCountryTreatment() {
        val treatment = splitViewModel.getCountryTreatment()
        when {
            treatment.equals("US_users") -> {
                // insert US_users code here
                binding.btnC.isEnabled = true
                binding.btnC.text = getString(R.string.enabled)
            }
            treatment.equals("CA_users") -> {
                // insert CA_users code here
                binding.btnC.isEnabled = false
            }
            else -> {
                // insert control code here
                unsupportedTreatment(SplitViewModel.SPLIT_SSG_PROTOTYPE_COUNTRY)
            }
        }
    }

    private fun setStudyTreatment() {
        val treatment = splitViewModel.getStudyTreatment()
        when {
            treatment.equals("Alpha_Study") -> {
                // insert Alpha_Study code here
                binding.btnD.isEnabled = false
            }
            treatment.equals("Beta_Study") -> {
                // insert Beta_Study code here
                binding.btnD.isEnabled = true
                binding.btnD.text = getString(R.string.enabled)
            }
            else -> {
                // insert control code here
                unsupportedTreatment(SplitViewModel.SPLIT_SSG_PROTOTYPE_STUDY)
            }
        }
    }

    private fun setRolloutTreatment() {
        val treatment = splitViewModel.getRolloutTreatment()
        when {
            treatment.equals("on") -> {
                // insert code for ON here
                binding.btnE.isEnabled = true
                binding.btnE.text = getString(R.string.enabled)
            }
            treatment.equals("off") -> {
                // insert code for OFF here
                binding.btnE.isEnabled = false
            }
            else -> {
                // insert control code here
                unsupportedTreatment(SplitViewModel.SPLIT_SSG_LIMIT_ROLLOUT)
            }
        }
    }

    @SuppressLint("Range")
    private fun setStyleTreatment() {
        val config = splitViewModel.getStyleTreatment()
        binding.btnF.isEnabled = true
        binding.btnF.text = config.text
        val color = Color.parseColor(config.color)
        binding.btnF.backgroundTintList = ColorStateList.valueOf(color)
        binding.btnF.setTextColor(Color.parseColor(config.textcolor))
    }

    override fun onDestroy() {
        super.onDestroy()
        splitViewModel.destroy()
    }

    private fun unsupportedTreatment(treatment: String) {
        Log.d("SplitIO", "setTreatment: treatment $treatment is not supported.")
    }

}