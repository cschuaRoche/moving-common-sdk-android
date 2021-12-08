package com.roche.ssg.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.roche.ssg.sample.databinding.ActivityMainBinding
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.amplitude.api.Amplitude

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

        setDrawerListener()
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

    private fun setDrawerListener() {
        binding.mainDrawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {
                logEvent("menu_open")
            }

            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    private fun logEvent(event: String) {
        try {
            val client = Amplitude.getInstance()
            client.logEvent(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}