package com.roche.roche.dis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.roche.roche.dis.databinding.ActivityMainBinding
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.navigateUp
import com.roche.apprecall.RecallApiClient
import com.roche.apprecall.RecallException
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.mainFragment
        ) //Pass the ids of fragments from nav_graph which you d'ont want to show back button in toolbar
            .setOpenableLayout(binding.mainDrawerLayout) //Pass the drawer layout id from activity xml
            .build()

        val toolbar = binding.root.findViewById<Toolbar>(R.id.main_tool_bar)
        setSupportActionBar(toolbar) //Set toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)// remove default titles of the fragments
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.mainNavigationView.setupWithNavController(navController)

        val mainSope = MainScope()
//        mainSope.launch {
//            kotlin.runCatching {
//                RecallApiClient.getAppRecall("https://floodlight.dhp-dev.dhs.platform.navify.com","com.roche.ssg.test.application","1.0","fr")
//            }.onSuccess {
//                Log.e("Success", it.toString())
//            }.onFailure {
//                Log.e("TAG","Error")
//            }
//
//        }

        mainSope.launch {
            try {
                val response = RecallApiClient().checkSaMDRecall(
                    "https://floodlight.dhp-dev.dhs.platform.navify.com",
                    "fr",
                    listOf("com.roche.ssg.test.samd.one:1.0.0", "com.roche.ssg.test.samd.two:1.0.1")
                )

                /*val response = RecallApiClient().checkAppRecall(
                    "https://floodlight.dhp-dev.dhs.platform.navify.com", "com.roche.ssg.test.application", "1.0", "fr"
                )
                Log.i("Response from client", "${response.updateAvailable}")*/
            } catch (e: RecallException) {
                Log.i("Response from client", "${e.message}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
//        // Allows NavigationUI to support proper up navigation or the drawer layout
//        // drawer menu, depending on the situation
        return findNavController(R.id.main_nav_host).navigateUp(appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.main_nav_host))
                || super.onOptionsItemSelected(item)
    }
}