package com.roche.roche.dis.ui

import android.annotation.SuppressLint
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
import com.roche.roche.dis.databinding.FragmentUserBinding
import com.roche.roche.dis.splitio.data.User
import com.roche.roche.dis.splitio.vm.SplitViewModel


class UserOneFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    private val splitViewModel: SplitViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)

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
        binding.tvStudy.text = getString(R.string.split_io_study, currentUser.study)
        binding.tvCountry.text = getString(R.string.split_io_country, currentUser.country)

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
            }
        })
    }

    private fun setVersionTreatment() {
        val treatment = splitViewModel.getVersionTreatment()
        when {
            treatment.equals("Green_button") -> {
                // insert on code here
                binding.btnA.isEnabled = true
            }
            treatment.equals("Red_button") -> {
                // insert off code here
                binding.btnB.isEnabled = true
            }
            else -> {
                // insert control code here
            }
        }
    }

    private fun setCountryTreatment() {
        val treatment = splitViewModel.getCountryTreatment()
        when {
            treatment.equals("US_users") -> {
                // insert US_users code here
                binding.btnC.isEnabled = true
            }
            treatment.equals("CA_users") -> {
                // insert CA_users code here
                binding.btnC.isEnabled = false
            }
            else -> {
                // insert control code here
            }
        }
    }

    private fun setStudyTreatment() {
        val treatment = splitViewModel.getStudyTreatment()
        when {
            treatment.equals("Alpha_Study") -> {
                // insert US_users code here
                binding.btnD.isEnabled = false
            }
            treatment.equals("Beta_Study") -> {
                // insert CA_users code here
                binding.btnD.isEnabled = true
            }
            else -> {
                // insert control code here
            }
        }
    }

    private fun setRolloutTreatment() {
        val treatment = splitViewModel.getRolloutTreatment()
        when {
            treatment.equals("on") -> {
                // insert US_users code here
                binding.btnE.isEnabled = true
            }
            treatment.equals("off") -> {
                // insert CA_users code here
                binding.btnE.isEnabled = false
            }
            else -> {
                // insert control code here
            }
        }
    }

    @SuppressLint("Range")
    private fun setStyleTreatment() {
        val config = splitViewModel.getStyleTreatment()
        binding.btnF.isEnabled = true
        binding.btnF.text = config.text
        binding.btnF.setBackgroundColor(Color.parseColor(config.color))
        binding.btnF.setTextColor(Color.parseColor(config.textcolor))
    }

    override fun onDestroy() {
        super.onDestroy()
        splitViewModel.destroy()
    }

}