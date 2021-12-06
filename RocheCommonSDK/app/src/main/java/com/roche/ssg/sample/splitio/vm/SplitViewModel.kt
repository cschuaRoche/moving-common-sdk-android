package com.roche.ssg.sample.splitio.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.roche.ssg.sample.data.users
import com.roche.ssg.sample.splitio.data.Config
import com.roche.ssg.sample.splitio.data.SelectedItem
import com.roche.ssg.sample.splitio.data.SplitIoFactory
import io.split.android.client.SplitClient
import io.split.android.client.SplitResult
import io.split.android.client.events.SplitEvent
import io.split.android.client.events.SplitEventTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplitViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var selectedUser: SelectedItem

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


    fun initClient(user: SelectedItem): LiveData<Boolean> {
        selectedUser = user
        val treatments = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            client = SplitIoFactory.getSplitClient(
                getApplication(),
                users[selectedUser.position].userID
            )
            client.on(SplitEvent.SDK_READY, object : SplitEventTask() {
                override fun onPostExecutionView(client: SplitClient?) {
                    treatments.postValue(true)
                }
            })
        }
        return treatments
    }

    fun getAllConfiguration() = users[selectedUser.position]

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