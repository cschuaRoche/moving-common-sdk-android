package com.roche.ssg.sample.push.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amazonaws.util.ClassLoaderHelper.getResourceAsStream
import com.google.gson.Gson
import com.roche.ssg.sample.auth.SessionManager
import com.roche.ssg.sample.auth.model.AWSConfigDTO
import com.roche.ssg.sample.auth.model.Failure
import com.roche.ssg.sample.auth.model.Success
import com.roche.ssg.sample.databinding.FragmentPushRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Locale

class RegisterPushFragment : Fragment() {

    private lateinit var binding: FragmentPushRegisterBinding
    private val scope = MainScope()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPushRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun initSession() {
        val jsonsting =
            Gson().fromJson(readFromAssetsAsString("awsconfig.json"), AWSConfigDTO::class.java)
        val configJson = JSONObject(Gson().toJson(jsonsting))

        scope.launch {
            when (SessionManager.initialize(
                context = requireContext(),
                configJson = configJson,
                applicationId = "",
                userRole = "",
                locale = Locale.getDefault().toLanguageTag()
            )) {
                is Success -> {
                    Log.e("RegisterPushFragment", "Successful to initialize SessionManager")

                }
                is Failure -> {
                    Log.e("RegisterPushFragment", "Failed to initialize SessionManager")
                }
            }
        }
        Log.e("TAG", "json: $configJson")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSession()
        binding.btnGetCognitoToken.setOnClickListener {
            if (SessionManager.isInitialized()) {
                login()
            } else {
                Toast.makeText(requireContext(), "Not able to login", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnRegister.setOnClickListener {

        }
        binding.btnUnregister.setOnClickListener {

        }
    }

    private fun readFromAssetsAsString(fileName: String): String? {
        var json: String? = null
        getResourceAsStream("assets/$fileName")?.run {
            try {
                json = bufferedReader().use { it.readText() }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                close()
            }
        }
        return json
    }

    private fun login() {
        scope.launch(Dispatchers.IO) {
            when (val result = SessionManager.login("devuser@mailinator.com", "Test@1234")) {
                is Success -> {
                    Log.e("TAG",""+SessionManager.getToken())
                    Log.e("TAG", "Successfully Logged in $SessionManager.getToken()")
                }

                is Failure -> {
                    Log.e("TAG", "Failed to Logged in")
                }
            }
        }
    }
}