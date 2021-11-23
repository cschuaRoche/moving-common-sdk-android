package com.roche.ssg.sample.amplitude.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amplitude.experiment.Experiment
import com.amplitude.experiment.ExperimentClient
import com.amplitude.experiment.ExperimentConfig
import com.amplitude.experiment.ExperimentUser
import com.amplitude.experiment.Variant
import com.google.gson.Gson
import com.roche.ssg.sample.data.users
import com.roche.ssg.sample.splitio.data.SelectedItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AmplitudeViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var client: ExperimentClient
    private lateinit var selectedUser: SelectedItem

    companion object {
        const val FLAG_APP_VERSION_ONE_ZERO = "app-version-1-0"
        const val FLAG_APP_VERSION_ONE_ONE = "app-version-1-1"
        const val FLAG_COHORT_ACME = "cohort-acme"
        const val FLAG_UNITED_STATES_USERS = "united-states-users"

        const val EXPERIMENT_SIGN_UP_CREATE_ACCOUNT = "signup-or-create-account"
        const val EXPERIMENT_TWENTY_PERCENT_TEST = "20-percent-test"
    }

    fun initClient(user: SelectedItem): LiveData<Boolean> {
        selectedUser = user
        val response = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiKey = "client-ZG6BB6P9EjfojouJ1RJ4Y56iW65JZPK1"

                // (2) Configure and initialize the experiment client
                val config = ExperimentConfig()
                client = Experiment.initialize(getApplication(), apiKey, config)

                // (3) Fetch variants for a user
                val user = ExperimentUser.builder()
                    .userId(users[selectedUser.position].userID)
                    .version(users[selectedUser.position].version)
                    .country(users[selectedUser.position].country)
                    .userProperty("study", users[selectedUser.position].study)
                    .build()
                client.fetch(user).get()
                response.postValue(true)
            } catch (e: Exception) {
                e.printStackTrace()
                response.postValue(false)
            }
        }

        return response
    }

    fun getFlagVersionOneZero(): Boolean {
        val variant = client.variant(FLAG_APP_VERSION_ONE_ZERO, Variant("off"))
        return variant.`is`("on")
    }

    fun getFlagVersionOneOne(): Boolean {
        val variant = client.variant(FLAG_APP_VERSION_ONE_ONE, Variant("off"))
        return variant.`is`("on")
    }

    fun getFlagUnitedStatesUsers(): Boolean {
        val variant = client.variant(FLAG_UNITED_STATES_USERS, Variant("off"))
        return variant.`is`("on")
    }

    fun getFlagStudy(): Boolean {
        val variant = client.variant(FLAG_COHORT_ACME, Variant("off"))
        return variant.`is`("on")
    }

    fun getExperimentPercentTest(): Boolean {
        val variant = client.variant(EXPERIMENT_TWENTY_PERCENT_TEST, Variant("off"))
        return variant.`is`("on")
    }

    fun getExperimentSignUpCreateAccount(): ExperimentNewUser? {
        val variant = client.variant(EXPERIMENT_SIGN_UP_CREATE_ACCOUNT)
        val payload: String? = variant.payload?.toString()
        val gson = Gson()
        return gson.fromJson(payload, ExperimentNewUser::class.java)
    }

    fun getAllConfiguration() = users[selectedUser.position]

}

data class ExperimentNewUser(val text: String, val color: String, val textColor: String)