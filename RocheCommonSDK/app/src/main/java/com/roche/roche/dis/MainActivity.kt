package com.roche.roche.dis

import android.os.Bundle
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