package com.roche.roche.dis.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.roche.roche.dis.R
import com.roche.roche.dis.databinding.FragmentUserBinding
import com.roche.roche.dis.splitio.data.SplitioFactory
import com.roche.roche.dis.splitio.data.User
import io.split.android.client.SplitClient
import io.split.android.client.SplitResult
import io.split.android.client.events.SplitEvent
import io.split.android.client.events.SplitEventTask
import kotlin.random.Random
import com.google.gson.Gson
import com.roche.roche.dis.splitio.data.Config


class UserOneFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    lateinit var client: SplitClient
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

        var userA = User("User1", "USER A", "us", "alpha", "1.1.0")
        var userB = User("User2", "USER B", "ca", "beta", "1.0.0")
        var userC = User("User3", "USER C", "us", "beta", "1.0.0")
        val list = listOf<User>(userA, userB, userC)
        val randomIndex = Random.nextInt(list.size);
        val randomUser = list[randomIndex]

        // Set title
        binding.user.text = randomUser.userName
        // Set the values
        binding.tvVersion1.text = getString(R.string.split_io_version, "1.0.0")
        binding.tvVersion2.text = getString(R.string.split_io_version, "1.1.0")
        binding.tvStudy.text = getString(R.string.split_io_study, randomUser.study)
        binding.tvCountry.text = getString(R.string.split_io_country, randomUser.country)

        client = context?.let { SplitioFactory.getSplitClient(it, randomUser.userID) }!!
        val attributesVersion: MutableMap<String, Any> = mutableMapOf()
        val attributesCountry: MutableMap<String, Any> = mutableMapOf()
        val attributesStudy: MutableMap<String, Any> = mutableMapOf()
        attributesVersion["version"] = randomUser.version
        attributesCountry["country"] = randomUser.country
        attributesStudy["study"] = randomUser.study
        client.on(SplitEvent.SDK_READY, object : SplitEventTask() {
            override fun onPostExecution(client: SplitClient?) {

                var treatment = client?.getTreatment("SSG_App_Version", attributesVersion)
                Log.e("TAG", "treatment: $treatment")
            }

            @SuppressLint("Range")
            override fun onPostExecutionView(client: SplitClient?) {
                val treatmentVersion = client?.getTreatment("SSG_App_Version", attributesVersion)
                if (treatmentVersion.equals("Green_button")) {
                    // insert on code here
                    binding.btnA.isEnabled = true
                } else if (treatmentVersion.equals("Red_button")) {
                    // insert off code here
                    binding.btnB.isEnabled = true
                } else {
                    // insert control code here
                }
                val treatmentCountry = client?.getTreatment("SSG_Prototype_Country", attributesCountry)
                if (treatmentCountry.equals("US_users")) {
                    // insert US_users code here
                    binding.btnC.isEnabled = true
                } else if (treatmentCountry.equals("CA_users")) {
                    // insert CA_users code here
                    binding.btnC.isEnabled = false
                } else {
                    // insert control code here
                }
                val treatmentStudy = client?.getTreatment("SSG_Prototype_Study", attributesStudy)
                Log.e("TAG","study:" + treatmentStudy)
                if (treatmentStudy.equals("Alpha_Study")) {
                    // insert Alpha_Study code here
                    binding.btnD.isEnabled = false
                } else if (treatmentStudy.equals("Beta_Study")) {
                    // insert Beta_Study code here
                    binding.btnD.isEnabled = true
                } else {
                    // insert control code here
                }
                val treatmentPercentage = client?.getTreatment("SSG_Limit_Rollout")
                if (treatmentPercentage.equals("on")) {
                    // insert on code here
                    binding.btnE.isEnabled = true
                } else if (treatmentPercentage.equals("off")) {
                    // insert off code here
                    binding.btnE.isEnabled = false
                } else {
                    // insert control code here
                }

                val splitResult: SplitResult? = client?.getTreatmentWithConfig("SSG_AB_Test",null)
                val gson = Gson()
                val config : Config = gson.fromJson(splitResult?.config(),Config::class.java)

                binding.btnF.isEnabled = true
                binding.btnF.text = config.text
                binding.btnF.setBackgroundColor(Color.parseColor(config.color))
                binding.btnF.setTextColor(Color.parseColor(config.textcolor))
                if (treatmentPercentage.equals("StyleA")) {
                    // insert StyleA code here

                } else if (treatmentPercentage.equals("StyleB")) {
                    // insert StyleB code here

                } else {
                    // insert control code here
                }
            }
        })
        return binding.root
    }

    override fun onDestroy() {
        client.destroy()
        super.onDestroy()
    }
}