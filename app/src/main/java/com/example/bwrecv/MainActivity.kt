package com.example.bwrecv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.bwrecv.Constant.Companion.TOKEN
import com.example.bwrecv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: Preferences
    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        sharedPreferences = Preferences(this)

        navHost = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        if (sharedPreferences.isLoggedIn()){
            TOKEN = sharedPreferences.getToken()

            if (navHost.navController.currentDestination!!.id != R.id.priceListFragment){
                navHost.navController.popBackStack()
                navHost.navController.navigate(R.id.priceListFragment)
            }

        }

    }
}