package com.roche.roche.dis

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.roche.roche.dis.databinding.ActivityMainBinding
import com.roche.roche.dis.staticcontent.DownloadStaticContent

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
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
            R.id.menu_download_static_content -> {
                if(isStoragePermissionGranted()) {
                    downloadStaticContent()
                }
            }
        }
        binding.mainDrawerLayout.close()
        return true
    }

    private fun downloadStaticContent() {
        Toast.makeText(this, "Start Downloading Content", Toast.LENGTH_LONG).show()
        DownloadStaticContent.downloadToFileSystem(
            this,
            "https://passport-static-content.tpp1-dev.platform.navify.com/com.roche.nrm_passport/configuration/countries/dev_countries.json",
            "dev_countries.json",
            ::callback
        )
    }

    private fun callback(isSuccess: Boolean) {
        if (isSuccess) {
            Toast.makeText(this, "Downloading content is successful", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Downloading content is failed", Toast.LENGTH_LONG).show()
        }
    }

    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun isStoragePermissionGranted(): Boolean {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadStaticContent()
                }
            }
        }
    }
}