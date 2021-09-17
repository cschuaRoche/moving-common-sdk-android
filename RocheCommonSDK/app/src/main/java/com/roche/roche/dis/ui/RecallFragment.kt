package com.roche.roche.dis.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roche.apprecall.data.RecallApiClient
import com.roche.apprecall.RecallException
import com.roche.roche.dis.databinding.FragmentRecallBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RecallFragment : Fragment() {

    private lateinit var binding: FragmentRecallBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecallBinding.inflate(inflater, container, false)
        setAppRecallListener()
        setSamdRecallListener()
        return binding.root
    }

    private fun setAppRecallListener() {
        binding.btnAppRecall.setOnClickListener {
            val mainScope = MainScope()
            /*"https://floodlight.dhp-dev.dhs.platform.navify.com",
            "com.roche.ssg.test.application",
            "1.0",
            "fr"*/
            mainScope.launch {
                try {
                    val response = RecallApiClient().checkAppRecall(
                        binding.etAppRecallUrl.text.toString(),
                        binding.etAppId.text.toString(),
                        binding.etAppVersion.text.toString(),
                        binding.etAppRecallCountry.text.toString()
                    )
                    binding.txtStatusAppRecall.visibility = View.VISIBLE
                    binding.txtStatusAppRecall.text = response.toString()
                } catch (e: RecallException) {
                    binding.txtStatusAppRecall.visibility = View.VISIBLE
                    binding.txtStatusAppRecall.text = "${e.status} ${e.message}"
                } catch (e: Exception) {
                    binding.txtStatusAppRecall.visibility = View.VISIBLE
                    binding.txtStatusAppRecall.text = "${e.message}"
                }
            }
        }
    }

    private fun setSamdRecallListener() {
        binding.btnSamdRecall.setOnClickListener {
            val mainScope = MainScope()

            /*"https://floodlight.dhp-dev.dhs.platform.navify.com",
            "fr",
            listOf("com.roche.ssg.test.samd.one:1.0.0", "com.roche.ssg.test.samd.two:1.0.1")*/

            mainScope.launch {
                try {
                    val response = RecallApiClient().checkSaMDRecall(
                        binding.etSamdRecallUrl.text.toString(),
                        binding.etSamdCountry.text.toString(),
                        binding.etSamds.text.toString().split(",")
                    )
                    binding.txtStatusSamdRecall.visibility = View.VISIBLE
                    binding.txtStatusSamdRecall.text = response.toString()
                } catch (e: RecallException) {
                    binding.txtStatusSamdRecall.visibility = View.VISIBLE
                    binding.txtStatusSamdRecall.text = "${e.status} ${e.message}"
                } catch (e: Exception) {
                    binding.txtStatusSamdRecall.visibility = View.VISIBLE
                    binding.txtStatusSamdRecall.text = "${e.message}"
                }
            }
        }
    }
}