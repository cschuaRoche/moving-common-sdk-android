package com.roche.roche.dis.splitio.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.roche.roche.dis.splitio.data.Config
import com.roche.roche.dis.splitio.data.SelectedItem
import com.roche.roche.dis.splitio.data.SplitIoFactory
import com.roche.roche.dis.splitio.data.User
import io.split.android.client.SplitClient
import io.split.android.client.SplitResult
import io.split.android.client.events.SplitEvent
import io.split.android.client.events.SplitEventTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplitViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var client: SplitClient

    companion object {
        const val ATTRIBUTE_VERSION = "version"
        const val ATTRIBUTE_COUNTRY = "country"
        const val ATTRIBUTE_STUDY = "study"

        const val SPLIT_SSG_PROTOTYPE_COUNTRY = "SSG_Prototype_Country"
        const val SPLIT_SSG_APP_VERSION = "SSG_App_Version"
        const val SPLIT_SSG_PROTOTYPE_STUDY = "SSG_Prototype_Study"
        const val SPLIT_SSG_LIMIT_ROLLOUT = "SSG_Limit_Rollout"
        const val SPLIT_SSG_AB_TEST = "SSG_AB_Test"
    }

    val users =
        arrayOf(
            User("User1", "USER A", "us", "alpha", "1.1.0"),
            User("User2", "USER B", "ca", "beta", "1.0.0"),
            User("User3", "USER C", "us", "beta", "1.0.0"),
            User("User4", "USER D", "us", "beta", "1.0.0"),
            User("User5", "USER E", "us", "beta", "1.0.0"),
            User("User6", "USER F", "us", "beta", "1.0.0"),
            User("User7", "USER G", "us", "beta", "1.0.0"),
            User("User8", "USER H", "us", "beta", "1.0.0"),
            User("User9", "USER I", "us", "beta", "1.0.0"),
            User("User10", "USER J", "us", "beta", "1.0.0")
        )

    var selectedUser = SelectedItem()

    fun initClient(): LiveData<Boolean> {
        val treatments = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            client = SplitIoFactory.getSplitClient(getApplication(), users[selectedUser.position].userID)
            client.on(SplitEvent.SDK_READY, object : SplitEventTask() {
                override fun onPostExecutionView(client: SplitClient?) {
                    treatments.postValue(true)
                }
            })
        }
        return treatments
    }

    fun getVersionTreatment(): String? =
        client.getTreatment(
            SPLIT_SSG_APP_VERSION,
            mapOf(ATTRIBUTE_VERSION to users[selectedUser.position].version)
        )


    fun getCountryTreatment(): String? =
        client.getTreatment(
            SPLIT_SSG_PROTOTYPE_COUNTRY,
            mapOf(ATTRIBUTE_COUNTRY to users[selectedUser.position].country)
        )

    fun getStudyTreatment(): String? =
        client.getTreatment(
            SPLIT_SSG_PROTOTYPE_STUDY,
            mapOf(ATTRIBUTE_STUDY to users[selectedUser.position].study)
        )

    fun getRolloutTreatment(): String? = client.getTreatment(SPLIT_SSG_LIMIT_ROLLOUT)

    fun getStyleTreatment(): Config {
        val splitResult: SplitResult? = client.getTreatmentWithConfig(SPLIT_SSG_AB_TEST, null)
        val gson = Gson()
        return gson.fromJson(splitResult?.config(), Config::class.java)
    }

    fun destroy() {
        if (this::client.isInitialized)
            client.destroy()
    }

}