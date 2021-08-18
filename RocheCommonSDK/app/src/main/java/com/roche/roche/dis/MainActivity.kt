package com.roche.roche.dis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.roche.roche.dis.databinding.ActivityMainBinding
import com.roche.roche.dis.utils.UnZipUtils
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var downloadViewModel: UserManualViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        downloadViewModel = UserManualViewModel()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.biometrics_nav_f
        ) //Pass the ids of fragments from nav_graph which you d'ont want to show back button in toolbar
            .setOpenableLayout(binding.mainDrawerLayout) //Pass the drawer layout id from activity xml
            .build()

        val toolbar = binding.root.findViewById<Toolbar>(R.id.main_tool_bar)
        setSupportActionBar(toolbar) //Set toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)// remove default titles of the fragments
        setupActionBarWithNavController(navController, appBarConfiguration)

        // set
        binding.mainNavigationView.setNavigationItemSelectedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean { //Setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    /**
     * menu Item select listener
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isCheckable = false
        when (menuItem.itemId) {
            R.id.menu_biometrics -> {
                val action = MainNavGraphDirections.actionToBiometrics()
                val options = NavOptions.Builder().setLaunchSingleTop(true).build()
                findNavController(R.id.main_nav_host).navigate(action, options)
            }
            R.id.menu_unzip -> {
                var path: File = filesDir
                val targetDirectory = "usermanuals"
                path = File(path.toString() + File.separator + targetDirectory)
                if (path.list().isNullOrEmpty()) {
                    Log.d("files", "Creating files")
                    UnZipUtils.unzipFromAsset("de_DE.zip", targetDirectory, applicationContext)
                } else {
                    Log.d("files", "Directory is not empty!")
                    getFilesRecursive(path)
                }
            }
            R.id.menu_user_manual -> {
                lifecycleScope.launch {
                    val response = downloadViewModel.syncUserManuals("https://passport-static-content.tpp1-dev.platform.navify.com/com.roche.nrm_passport/docs/floodlight.json", LocaleType.EN_US)
                    Log.d("usermanual", "response: $response")
                }
            }
        }
        binding.mainDrawerLayout.close()
        return true
    }

    private fun getFilesRecursive(pFile: File) {
        for (files in pFile.listFiles()) {
            if (files.isDirectory) {
                getFilesRecursive(files)
            } else {
                Log.d("files", "$files")
            }
        }
    }
}