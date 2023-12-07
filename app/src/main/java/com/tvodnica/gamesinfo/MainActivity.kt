package com.tvodnica.gamesinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.tvodnica.gamesinfo.helpers.FIRST_LAUNCH_DONE

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(FIRST_LAUNCH_DONE, false)){
            findNavController(R.id.my_nav_host_fragment).navigate(R.id.action_gamesList_to_welcome)
        }
    }
}