package com.roche.ssg.sample.login.navify

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.roche.ssg.sample.databinding.FragmentNavifyLoginBinding

class NavifyLoginFragment : Fragment() {

    private lateinit var mBinding: FragmentNavifyLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentNavifyLoginBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    private fun login() {
        resultLauncher.launch(Intent(activity, LoginActivity::class.java))
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            Log.i("Testing", "Result code ${result.resultCode}")
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent = result.data!!
                val code = data.getStringExtra("auth_code")
                //Log.i("Testing", "Received code $code")
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Log.i("Testing", "Authentication cancelled")
            }
        }
}