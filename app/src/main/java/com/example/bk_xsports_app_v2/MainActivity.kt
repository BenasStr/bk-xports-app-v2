package com.example.bk_xsports_app_v2

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bk_xsports_app_v2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val args: MainActivityArgs by navArgs()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadToken(args.token)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_my_list,
                R.id.navigation_training,
                R.id.navigation_progress,
                R.id.navigation_explore,
                R.id.navigation_account
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun loadToken(token: String) {
        val sharedPreference = getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor?.putString("token", token)
        editor?.apply()
    }
}