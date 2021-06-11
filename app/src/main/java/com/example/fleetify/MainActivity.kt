package com.example.fleetify

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fleetify.Fragments.HomeFragment
import com.example.fleetify.Fragments.NotificationsFragment
import com.example.fleetify.Fragments.ProfileFragment
import com.example.fleetify.Fragments.SearchFragment

class MainActivity : AppCompatActivity() {

    internal var selectedFragment: Fragment? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {

                selectedFragment=HomeFragment()
            }
            R.id.nav_search -> {
                selectedFragment=SearchFragment()
            }
            R.id.nav_add_trip -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notifications -> {
                selectedFragment=NotificationsFragment()
            }
            R.id.nav_profile -> {
                selectedFragment=ProfileFragment()
            }
        }
        if(selectedFragment != null){
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                selectedFragment!!
            ).commit()
        }

        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)



        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            HomeFragment()
        ).commit()

    }





}