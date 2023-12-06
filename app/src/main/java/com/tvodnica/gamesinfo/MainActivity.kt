package com.tvodnica.gamesinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("firstLaunchDone", false)){
            findNavController(R.id.my_nav_host_fragment).navigate(R.id.action_welcome_to_gamesList)
        }
    }
}