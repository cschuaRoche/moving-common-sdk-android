package com.roche.ssg.sample.amplitude.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplitude.experiment.Experiment
import com.amplitude.experiment.ExperimentClient
import com.amplitude.experiment.ExperimentConfig
import com.amplitude.experiment.ExperimentUser
import com.amplitude.experiment.Variant
import com.roche.ssg.sample.data.users
import com.roche.ssg.sample.splitio.data.SelectedItem

class AmplitudeViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var client: ExperimentClient
    private lateinit var selectedUser: SelectedItem

    companion object {
        const val FLAG_APP_VERSION_ONE_ZERO = "app-version-1-0"
        const val FLAG_APP_VERSION_ONE_ONE = "app-version-1-1"
        const val FLAG_FRANCE_ENABLEMENT = "france-enablement"
        const val FLAG_UNITED_STATES_USERS = "united-states-users"
    }

    fun initClient(user: SelectedItem): LiveData<Boolean> {
        selectedUser = user
        val response = MutableLiveData<Boolean>()
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
                //.country("United States")
                .build()
            client.fetch(user).get()
            response.postValue(true)
        } catch (e: Exception) {
            e.printStackTrace()
            response.postValue(false)
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

    fun getAllConfiguration() = users[selectedUser.position]


}